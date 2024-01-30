package org.dam.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.dam.common.exception.ClientException;
import org.dam.common.exception.ServiceException;
import org.dam.common.page.PageResponse;
import org.dam.controller.BackupController;
import org.dam.controller.WebSocketServer;
import org.dam.entity.*;
import org.dam.entity.request.BackupFileRequest;
import org.dam.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import org.dam.common.utils.FileUtils;

import static org.dam.service.impl.BackupTaskServiceImpl.setProgress;

/**
 * @Author dam
 * @create 2024/1/19 13:53
 */
@Slf4j
@Service
public class BackupServiceImpl implements BackupService {

    @Autowired
    private BackupFileHistoryService backupFileHistoryService;
    @Autowired
    private BackupFileService backupFileService;
    @Autowired
    private BackupSourceService backupSourceService;
    @Autowired
    private BackupTargetService backupTargetService;
    @Autowired
    private ThreadPoolExecutor executor;
    @Autowired
    private BackupTaskService backupTaskService;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private BackupController backupController;
    @Autowired
    private FileMessageService fileMessageService;

    private Random random = new Random();

    /**
     * 对指定的备份源进行备份
     *
     * @param sourceId
     */
    @Override
    public void backupBySourceId(String sourceId) {
        // 检查 备份源目录是否存在 和 准备好备份目标目录
        List<Task> taskList = checkSourceAndTarget(sourceId);
        // 更新数据源备份次数
        backupSourceService.updateBackupNum(sourceId);
        // 执行备份
        CompletableFuture[] futureArr = new CompletableFuture[taskList.size()];
        for (int i = 0; i < taskList.size(); i++) {
            int finalI = i;
            futureArr[i] = CompletableFuture.runAsync(() -> backUpByTask(taskList.get(finalI)), executor);
        }
        CompletableFuture.allOf(futureArr).join();

        // 计算完成，移除相应数据源ID
        if (backupController.sourceIDSet.contains(sourceId)) {
            backupController.sourceIDSet.remove(sourceId);
        }
    }

    /**
     * 检查数据，删除 无效备份信息 和 已备份文件
     * 什么叫无效？简单来说就是，已备份文件和原文件对应不上，或者说原文件被删除了
     *
     * @param sourceId
     */
    @Override
    public void clearBySourceId(String sourceId) {
        long current = 1;
        // 存储要删除的文件
        List<Long> removeBackupFileIdList = new ArrayList<>();
        List<String> removeBackupTargetFilePathList = new ArrayList<>();
        BackupFileRequest backupFileRequest = new BackupFileRequest();
        while (true) {
            // 分页查询出数据，即分批检查，避免数据量太大，占用太多内存
            backupFileRequest.setCurrent(current);
            backupFileRequest.setSize(1000L);
            backupFileRequest.setBackupSourceId(Long.parseLong(sourceId));
            PageResponse<BackupFile> backupFilePageResponse = backupFileService.pageBackupFile(backupFileRequest);

            if (backupFilePageResponse.getRecords().size() > 0) {
                for (BackupFile backupFile : backupFilePageResponse.getRecords()) {
                    // 获取备份文件的路径
                    // todo 待优化为存储的时候，不存储整一个路径，节省数据库空间，只存储从根目录开始后面的路径，后面获取整个路径再进行拼接
                    String sourceFilePath = backupFile.getSourceFilePath();
                    File sourceFile = new File(sourceFilePath);
                    if (!sourceFile.exists()) {
                        // --if-- 如果原目录该文件已经被删除，则删除
                        removeBackupFileIdList.add(backupFile.getId());
                        removeBackupTargetFilePathList.add(backupFile.getTargetFilePath());
                    }
                }
                // 换一页来检查
                current += 1;
            } else {
                // 查不出数据了，说明检查完了
                break;
            }
        }

        if (removeBackupFileIdList.size() > 0) {
            // 批量删除无效备份文件
            backupFileService.removeByIds(removeBackupFileIdList);
            // 删除无效的已备份文件
            for (String backupTargetFilePath : removeBackupTargetFilePathList) {
                File removeFile = new File(backupTargetFilePath);
                if (removeFile.exists()) {
                    boolean delete = FileUtils.recursionDeleteFiles(removeFile);
                    if (!delete) {
                        throw new ServiceException("文件无法删除");
                    }
                }
            }
            // 批量删除无效备份文件对应的备份记录
            backupFileHistoryService.removeByFileIds(removeBackupFileIdList);
        }
    }

    /**
     * 备份一个数据源的数据
     *
     * @param task
     */
    private void backUpByTask(Task task) {
        BackupSource backupSource = task.getSource();
        BackupTarget target = task.getTarget();
        // 找到备份目录下面的所有文件
        Statistic sta = new Statistic(0, 0, 0, 0, new Date().getTime() / 1000);
        getSonFileNum(new File(backupSource.getRootPath()), sta);
        log.info("当前数据源（id={}）下的总文件数量:{}，总字节数：{}", backupSource.getId(), sta.totalBackupFileNum, sta.totalBackupByteNum);
        // 将任务插入到数据库中
        String targetRootPath = getTargetRootPath(task, backupSource, target);
        BackupTask backupTask = new BackupTask(backupSource.getRootPath(), targetRootPath,
                sta.totalBackupFileNum, 0, sta.totalBackupByteNum, 0L, 0, "0.0", 0L, new Date());
        backupTaskService.save(backupTask);
        setProgress(backupTask);
        log.info("发送任务消息，通知前端任务创建成功");
        webSocketServer.sendMessage(JSON.toJSONString(backupTask), WebSocketServer.usernameAndSessionMap.get("Admin"));

        log.info("任务创建成功，开始备份");

        // 记录每个文件路径及其对应的id，如/Users/mac/Dev/BackUpTest/dasdasdasd.txt=>515351
        Map<String, Long> filePathAndIdMap = new HashMap<>();
        // 查询出当前数据源中所有已经备份过的文件
        QueryWrapper<BackupFile> backupFileQueryWrapper = new QueryWrapper<>();
        backupFileQueryWrapper.eq("backup_source_id", backupSource.getId());
        if (backupSource.getBackupType() == 0) {
            // --if-- 分散备份的时候，目标id不确定，所以都查询出来
            backupFileQueryWrapper.eq("backup_target_id", backupSource.getBackupType());
        }
        List<BackupFile> backupFileList = backupFileService.list(backupFileQueryWrapper);
        for (BackupFile backupFile : backupFileList) {
            filePathAndIdMap.put(backupFile.getSourceFilePath(), backupFile.getId());
        }
        // 将数据源的数据备份到多个目标目录下面
        sta.second = new Date().getTime() / 1000;

        // 查询出文件信息的树形结构
        List<FileMessage> fileMessageList = fileMessageService.list(new QueryWrapper<FileMessage>().
                eq("backup_source_id", backupSource.getId()).eq("father_id", 0L));
        backUpAllFiles(task, new File(backupSource.getRootPath()), backupSource, target, task.getTargetList(), filePathAndIdMap, sta,
                "", backupTask.getId(), backupTask.getCreateTime(),
                0L, fileMessageList);

        // 备份结束，修改备份任务的状态为完成
        backupTask.setBackupStatus(2);
        backupTask.setFinishFileNum(sta.getTotalBackupFileNum());
        backupTask.setFinishByteNum(sta.getFinishBackupByteNum());
        backupTask.setEndTime(new Date());
        backupTaskService.updateById(backupTask);
        setProgress(backupTask);
        backupTask.setBackupTime(backupTask.getEndTime().getTime() - backupTask.getCreateTime().getTime());
        log.info("发送任务消息，通知前端任务备份完成");
        webSocketServer.sendMessage(JSON.toJSONString(backupTask), WebSocketServer.usernameAndSessionMap.get("Admin"));
    }

    /**
     * 获取备份目标目录
     *
     * @param task
     * @param backupSource
     * @param target
     * @return
     */
    private static String getTargetRootPath(Task task, BackupSource backupSource, BackupTarget target) {
        String targetRootPath;
        if (backupSource.getBackupType() == 1) {
            targetRootPath = "分散备份：\n";
            for (BackupTarget backupTarget : task.getTargetList()) {
                targetRootPath += backupTarget.getTargetRootPath() + "\n";
            }
        } else {
            targetRootPath = target.getTargetRootPath();
        }
        return targetRootPath;
    }

    /**
     * 将一个 父文件夹 的所有数据备份到目标目录中
     *
     * @param fatherFile
     * @param backupSource
     * @param backupTarget
     * @param filePathAndIdMap
     * @param statistic
     * @param middlePath
     */
    private void backUpAllFiles(Task task, File fatherFile, BackupSource backupSource, BackupTarget backupTarget, List<BackupTarget> targetList,
                                Map<String, Long> filePathAndIdMap, Statistic statistic, String middlePath,
                                Long backupTaskId, Date taskBackupStartTime,
                                Long fatherId, List<FileMessage> fileMessageList) {
        File[] fileArr = fatherFile.listFiles();
        HashMap<String, FileMessage> fileNameAndFileMessageMap = new HashMap<>();
        if (backupSource.getBackupType() == 1 && fileMessageList != null) {
            List<Long> removeFileMessageIdList = new ArrayList<>();
            HashSet<String> fileNameSet = new HashSet<>();
            for (int i = 0; i < fileArr.length; i++) {
                fileNameSet.add(fileArr[i].getName());
            }
            for (FileMessage fileMessage : fileMessageList) {
                fileNameAndFileMessageMap.put(fileMessage.getFileName(), fileMessage);
                if (!fileNameSet.contains(fileMessage.getFileName())) {
                    removeFileMessageIdList.add(fileMessage.getId());
                }
            }
            // 如果数据源中没有相应文件，将其也从数据库中删除
            fileMessageService.recursionRemoveFileMessage(removeFileMessageIdList);
        }
        for (File file : fileArr) {
//            if (file.toString().indexOf("/.") != -1 || file.toString().indexOf("\\.") != -1) {
//                continue;
//            }

            if (file.isDirectory()) {
                // --if-- 若是目录，先在目标目录下创建目录，然后递归备份文件
                String targetFilePath = getTargetFilePath(backupSource, backupTarget, targetList, middlePath, file);

                // 查询备份文件数据表是否已经包含这个记录
                QueryWrapper<BackupFile> queryWrapper = new QueryWrapper<BackupFile>()
                        .eq("source_file_path", targetFilePath)
                        .eq("backup_source_id", backupSource.getId());
                if (backupSource.getBackupType() == 0) {
                    queryWrapper.eq("backup_target_id", backupTarget.getId());
                }
                BackupFile backupFileOnDatabase = backupFileService.getOne(queryWrapper);

                File targetFile = new File(targetFilePath);
                if (!targetFile.exists()) {
                    boolean mkdirs = targetFile.mkdirs();
                    if (mkdirs) {
                        // 将目录插入到数据库中
                        saveBackupFileDir(backupSource, backupTarget, file.getPath(), targetFilePath, backupFileOnDatabase);
                    } else {
                        throw new ServiceException("无法创建目录，可能是权限不够");
                    }
                } else {
                    saveBackupFileDir(backupSource, backupTarget, file.getPath(), targetFilePath, backupFileOnDatabase);
                }

                // 如果文件信息还没有被存储到数据库中
                Long curFileMessageId = null;
                if (backupSource.getBackupType() == 1 && fileNameAndFileMessageMap.get(file.getName()) == null) {
                    FileMessage fileMessage = new FileMessage();
                    fileMessage.setFileName(file.getName());
                    fileMessage.setBackupSourceId(backupSource.getId());
                    fileMessage.setSourceFilePath(file.getPath());
                    // 分散存储的时候，不会创建文件夹，因此直接为空就行
                    fileMessage.setTargetFilePath(null);
                    // 目录的后缀为空
                    fileMessage.setFileSuffix(null);
                    fileMessage.setFileLength(0L);
                    fileMessage.setFatherId(fatherId);
                    fileMessage.setIsCompress(backupSource.getIsCompress());
                    fileMessage.setFileType(0);
                    fileMessage.setIsContainFile(file.listFiles().length > 0 ? 1 : 0);
                    fileMessageService.save(fileMessage);
                    curFileMessageId = fileMessage.getId();
                } else if (backupSource.getBackupType() == 1 && fileNameAndFileMessageMap.get(file.getName()) != null) {
                    curFileMessageId = fileNameAndFileMessageMap.get(file.getName()).getId();
                }

                // 是否存在对应的文件信息，如果备份类型不是是分散存储，那么文件信息肯定不存在
                boolean haveFileMessage = backupSource.getBackupType() == 1 && fileNameAndFileMessageMap.get(file.getName()) != null;
                List<FileMessage> children = null;
                if (haveFileMessage) {
                    children = new ArrayList<>();
                    fileMessageService.list(new QueryWrapper<FileMessage>().
                            eq("backup_source_id", backupSource.getId()).
                            eq("father_id", curFileMessageId));
                }
                backUpAllFiles(task, file, backupSource, backupTarget, targetList, filePathAndIdMap, statistic,
                        middlePath + file.getName() + File.separator, backupTaskId, taskBackupStartTime,
                        curFileMessageId, children);
            }
            if (file.isFile()) {
                // --if-- 若是文件，执行备份操作
                try {
                    execBackUp(task, backupSource, backupTarget, targetList, file.toString(), filePathAndIdMap,
                            statistic, middlePath, backupTaskId, taskBackupStartTime, fatherId, fileNameAndFileMessageMap);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void saveBackupFileDir(BackupSource backupSource, BackupTarget backupTarget, String sourceFilePath, String targetFilePath, BackupFile backupFileOnDatabase) {
        if (backupFileOnDatabase == null) {
            BackupFile backupFile = new BackupFile();
            backupFile.setBackupSourceId(backupSource.getId());
            if (backupSource.getBackupType() == 0) {
                backupFile.setBackupTargetId(backupTarget.getId());
            } else {
                backupFile.setBackupTargetId(0L);
            }
            backupFile.setSourceFilePath(sourceFilePath);
            backupFile.setTargetFilePath(targetFilePath);
            backupFile.setBackupNum(0);
            backupFile.setLastBackupTime(new DateTime());
            backupFile.setFileType(0);
            backupFileService.save(backupFile);
        }
    }

    /**
     * @param source
     * @param target
     * @param backupSourceFilePath
     * @param filePathAndIdMap
     * @param statistic
     * @param middlePath
     * @throws SQLException
     * @throws IOException
     */
    private void execBackUp(Task task, BackupSource source, BackupTarget target, List<BackupTarget> targetList, String backupSourceFilePath,
                            Map<String, Long> filePathAndIdMap, Statistic statistic, String middlePath,
                            Long backupTaskId, Date taskBackupStartTime,
                            Long fatherId, HashMap<String, FileMessage> fileNameAndFileMessageMap) throws SQLException, IOException {
        System.out.println("执行execBackUp");
        // todo 检查这里是否合理
       /* if (backupSourceFilePath.indexOf("/.") != -1 || backupSourceFilePath.indexOf("\\.") != -1) {
            // 不拷贝.开头的文件夹和文件
            return;
        }*/
        File backupSourceFile = new File(backupSourceFilePath);
        // 目标路径名称
        String targetFilePath = getTargetFilePath(source, target, targetList, middlePath, backupSourceFile);

        if (source.getBackupType() == 1 && fileNameAndFileMessageMap.get(backupSourceFile.getName()) == null) {
            FileMessage fileMessage = new FileMessage();
            fileMessage.setFileName(backupSourceFile.getName());
            fileMessage.setBackupSourceId(source.getId());
            fileMessage.setSourceFilePath(backupSourceFile.getPath());
            // 分散存储的时候，不会创建文件夹，因此直接为空就行
            fileMessage.setTargetFilePath(targetFilePath);
            // 目录的后缀为空
            if (backupSourceFile.getName().contains(".")) {
                fileMessage.setFileSuffix(backupSourceFile.getName().substring(backupSourceFile.getName().lastIndexOf(".")));
            } else {
                fileMessage.setFileSuffix("该文件无后缀");
            }
            fileMessage.setFileLength(backupSourceFile.length());
            fileMessage.setFatherId(fatherId);
            fileMessage.setIsCompress(source.getIsCompress());
            fileMessage.setFileType(1);
            fileMessage.setIsContainFile(0);
            fileMessageService.save(fileMessage);
        }

        long fileId;
        long targetId = source.getBackupType() == 0 ? target.getId() : 0;
        if (!filePathAndIdMap.containsKey(backupSourceFilePath)) {
            // --if-- 文件还没有备份过，将其插入到数据库中，并取出id

            // 第一次拷贝该文件，将文件信息存储到数据库中
            BackupFile backupFile = new BackupFile();
            backupFile.setBackupSourceId(source.getId());
            backupFile.setBackupTargetId(targetId);
            backupFile.setSourceFilePath(backupSourceFilePath.replace("\\", "\\\\").replace("'", "\\'"));
            backupFile.setTargetFilePath(targetFilePath);
            backupFile.setBackupNum(0);
            backupFile.setLastBackupTime(new DateTime());
            backupFile.setFileType(1);
            backupFileService.save(backupFile);
            // 查询出其在数据库中对应的ID
            fileId = backupFile.getId();
        } else {
            fileId = filePathAndIdMap.get(backupSourceFilePath);
        }
        // 查询出该文件的最后一次的备份历史
        BackupFileHistory fileHistory = backupFileHistoryService.getLastBackupHistory(fileId);
        // 判断文件是否有修改
        boolean isNeedBackup = true;
        if (fileHistory != null) {
            long lastModify = fileHistory.getModifyTime();
            long fileSize = fileHistory.getFileSize();
            String historyMD5 = fileHistory.getMd5();
            if (lastModify == backupSourceFile.lastModified() && fileSize == backupSourceFile.length()) {
                // 如果文件的 修改时间 和 文件大小 都和数据库中的对应，认为文件没有被修改，无需备份
                isNeedBackup = false;
            }
            // 如果修改时间不一样，文件大小一样，追加校验一次hash，如果hash一样，则更新修改时间，不执行备份
            if (lastModify != backupSourceFile.lastModified() && fileSize == backupSourceFile.length()) {
                // 只要输入一样，输出的MD5码就是一样的，如果md5一样，不执行备份
                String md5str = DigestUtil.md5Hex(new FileInputStream(backupSourceFile));
                if (md5str.equals(historyMD5)) {
                    isNeedBackup = false;
                }
            }
        }
        // 执行具体的备份
        System.out.println("开始执行备份");
        if (isNeedBackup) {
            if (!execBackupSingleFile(backupSourceFilePath, targetId, targetFilePath, fileId, backupTaskId)) {
                log.error("备份出错");
                return;
            } else {
                // 更新文件的备份次数
                backupFileService.updateBackupNum(fileId);
            }
        }

        // 每隔一秒输出一下拷贝进度
        statistic.finishBackupFileNum++;
        statistic.finishBackupByteNum += backupSourceFile.length();
        long curTime = System.currentTimeMillis();
        if ((curTime / 1000) != statistic.second) {
            statistic.second = curTime / 1000;
            log.info("文件数量：拷贝进度:" + statistic.finishBackupFileNum * 100.0 / statistic.totalBackupFileNum + "%  " + statistic.finishBackupFileNum + "/" + statistic.totalBackupFileNum +
                    "； 文件大小：拷贝进度:" + statistic.finishBackupByteNum * 100.0 / statistic.totalBackupByteNum + "%  " + statistic.finishBackupByteNum + "/" + statistic.totalBackupByteNum);
            BackupTask backupTask = new BackupTask();
            backupTask.setBackupSourceRoot(source.getRootPath());
            backupTask.setBackupTargetRoot(getTargetRootPath(task, source, target));
            backupTask.setBackupStatus(1);
            backupTask.setId(backupTaskId);
            backupTask.setFinishFileNum(statistic.finishBackupFileNum);
            backupTask.setFinishByteNum(statistic.finishBackupByteNum);
            backupTask.setTotalFileNum(statistic.totalBackupFileNum);
            backupTaskService.updateById(backupTask);
            setProgress(backupTask);
            backupTask.setBackupTime(curTime - taskBackupStartTime.getTime());
            log.info("发送任务消息，通知前端备份进度变化");
            webSocketServer.sendMessage(JSON.toJSONString(backupTask), WebSocketServer.usernameAndSessionMap.get("Admin"));
        }
    }

    private String getTargetFilePath(BackupSource source, BackupTarget target, List<BackupTarget> targetList, String middlePath, File backupSourceFile) {
        String targetFilePath;
        if (source.getBackupType() == 0) {
            // --if-- 集中备份
            targetFilePath = getTargetPath(backupSourceFile, target, middlePath);
        } else {
            // --if-- 分散备份
            BackupTarget bestBackupTarget = getBestBackupTarget(backupSourceFile.length(), targetList);
            targetFilePath = bestBackupTarget.getTargetRootPath() + File.separator + backupSourceFile.getName();
        }
        return targetFilePath;
    }

    /**
     * 执行 单个文件 的拷贝
     *
     * @param sourceFilePath 需要备份的源文件路径
     * @param target         备份的目标目录
     * @param targetFilePath 备份的目标文件路径
     * @param backupFileId   存储到数据库中的备份文件ID
     * @return
     * @throws IOException
     */
    private boolean execBackupSingleFile(String sourceFilePath, long targetId, String targetFilePath, long backupFileId, long backupTaskId) throws IOException {
        Date start = new Date();
        File backupSourceFile = new File(sourceFilePath);
        FileInputStream sourceFileStream = new FileInputStream(backupSourceFile);
        String md5str;
        try {
            md5str = DigestUtil.md5Hex(sourceFileStream);
        } finally {
            sourceFileStream.close();
        }

        try {
            backupWithFileChannel(backupSourceFile, new File(targetFilePath));
            log.info("备份文件成功，从" + sourceFilePath + "到" + targetFilePath);
        } catch (Exception e) {
            log.info("备份文件失败，从" + sourceFilePath + " 到 " + targetFilePath);
            return false;
        }
        // 保存文件备份历史
        BackupFileHistory history = new BackupFileHistory();
        history.setBackupFileId(backupFileId);
        history.setModifyTime(backupSourceFile.lastModified());
        history.setFileSize(backupSourceFile.length());
        history.setBackupStartTime(start);
        history.setBackupEndTime(new Date());
        history.setBackupSourceFilePath(sourceFilePath);
        history.setBackupTargetFilePath(targetFilePath);
        history.setBackupTargetRootId(targetId);
        history.setMd5(md5str);
        history.setBackupTaskId(backupTaskId);
        backupFileHistoryService.save(history);
        return true;
    }

    /**
     * 将 source 备份到 target
     *
     * @param source
     * @param target
     * @throws IOException
     */
    private static void backupWithFileChannel(File source, File target) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(target).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputChannel != null) {
                inputChannel.close();
            }
            if (outputChannel != null) {
                outputChannel.close();
            }
        }
    }


    /**
     * 获取完整的目标文件路径
     *
     * @param file
     * @param backupTarget
     * @param middlePath   层层拼接之后的路径
     * @return
     */
    private static String getTargetPath(File file, BackupTarget backupTarget, String middlePath) {
        return backupTarget.getTargetRootPath() + File.separator + middlePath + file.getName();
    }

    /**
     * 获取一个目录下面的所有文件数量
     *
     * @param file
     */
    private void getSonFileNum(File file, Statistic sta) {
        File[] fs = file.listFiles();
        for (File f : fs) {
//            if (f.toString().indexOf("/.") != -1 || f.toString().indexOf("\\.") != -1) {
//                continue;
//            }
            if (f.isDirectory()) {
                // --if-- 若是目录，则递归统计该目录下的文件数量
                getSonFileNum(f, sta);
            }
            if (f.isFile()) {
                // --if-- 若是文件，添加到文件夹中
                sta.totalBackupFileNum++;
                sta.totalBackupByteNum += f.length();
            }
        }
    }

    /**
     * 检查 备份源目录是否存在 和 准备好备份目标目录
     *
     * @param sourceId
     */
    private List<Task> checkSourceAndTarget(String sourceId) {
        BackupSource source = backupSourceService.getById(sourceId);
        if (source == null) {
            throw new ClientException("id对应备份源信息不存在于数据库中");
        }
        File sourceFile = new File(source.getRootPath());
        if (!sourceFile.exists()) {
            throw new ServiceException("备份源目录不存在，请检查备份源是否被删除");
        }
        // 查询备份源对应的所有 备份目标目录 准备好相关的目录
        List<BackupTarget> backupTargetList = backupTargetService.list(new QueryWrapper<BackupTarget>().eq("backup_source_id", source.getId()));
        if (backupTargetList.size() == 0) {
            throw new ClientException("没有为 备份源 配置 备份目标目录，请先配置 备份目标目录");
        }
        for (BackupTarget backupTarget : backupTargetList) {
            File file = new File(backupTarget.getTargetRootPath());
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
                if (!mkdir) {
                    throw new ServiceException("目标目录创建失败");
                }
            }
        }
        List<Task> taskList = null;
        if (source.getBackupType() == 0) {
            taskList = backupTargetList.stream().map(item -> {
                return new Task(source, item, null);
            }).collect(Collectors.toList());
        } else if (source.getBackupType() == 1) {
            Task task = new Task(source, null, backupTargetList);
            taskList = new ArrayList<>();
            taskList.add(task);
        }
        return taskList;
    }

    /**
     * 获取一个有效的目标目录
     * 如果有多个目标目录，则选择剩余空间最大的目录
     *
     * @param needSpace
     * @return
     */
    private BackupTarget getBestBackupTarget(long needSpace, List<BackupTarget> targetList) {
        List<BackupTarget> bestTargetList = new ArrayList<>();
        long maxSpace = 0;
        // 找到剩余空间足够且最大的那个目录
        for (BackupTarget backupTarget : targetList) {
            File file = new File(backupTarget.getTargetRootPath());
            if (file.getFreeSpace() > needSpace && file.getFreeSpace() > maxSpace) {
                maxSpace = file.getFreeSpace();
                bestTargetList.clear();
                bestTargetList.add(backupTarget);
            } else if (file.getFreeSpace() > needSpace && file.getFreeSpace() == maxSpace) {
                bestTargetList.add(backupTarget);
            }
        }
        if (bestTargetList.size() == 0) {
            throw new ServiceException("所有备份目标目录下面都没有足够的空间，无法再执行备份");
        }
        return bestTargetList.get(random.nextInt(bestTargetList.size()));
    }

}
