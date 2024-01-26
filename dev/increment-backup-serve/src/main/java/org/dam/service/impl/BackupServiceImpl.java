package org.dam.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.dam.common.exception.ClientException;
import org.dam.common.exception.ServiceException;
import org.dam.controller.BackupController;
import org.dam.controller.WebSocketServer;
import org.dam.entity.*;
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
     * 备份一个数据源的数据
     *
     * @param task
     */
    private void backUpByTask(Task task) {
        BackupSource source = task.getSource();
        BackupTarget target = task.getTarget();
        // 找到备份目录下面的所有文件
        Statistic sta = new Statistic(0, 0, 0, 0, new Date().getTime() / 1000);
        getSonFileNum(new File(source.getRootPath()), sta);
        log.info("当前数据源（id={}）下的总文件数量:{}，总字节数：{}", source.getId(), sta.totalBackupFileNum, sta.totalBackupByteNum);
        // 将任务插入到数据库中
        BackupTask backupTask = new BackupTask(source.getRootPath(), task.getTarget().getTargetRootPath(),
                sta.totalBackupFileNum, 0, sta.totalBackupByteNum, 0L, 0, "0.0", 0L, new Date());
        backupTaskService.save(backupTask);
        setProgress(backupTask);
        log.info("发送任务消息，通知前端任务创建成功");
        webSocketServer.sendMessage(JSON.toJSONString(backupTask), WebSocketServer.usernameAndSessionMap.get("Admin"));

        log.info("任务创建成功，开始备份");

        // 记录每个文件路径及其对应的id，如/Users/mac/Dev/BackUpTest/dasdasdasd.txt=>515351
        Map<String, Long> filePathAndIdMap = new HashMap<>();
        // 查询出当前数据源中所有已经备份过的文件
        List<BackupFile> backupFileList = backupFileService.list(new QueryWrapper<BackupFile>().
                eq("backup_source_id", source.getId()).
                eq("backup_target_id", target.getId()));
        for (BackupFile backupFile : backupFileList) {
            filePathAndIdMap.put(backupFile.getFilePath(), backupFile.getId());
        }
        // 将数据源的数据备份到多个目标目录下面
        BackupTarget backupTarget = task.getTarget();
        sta.timestamp = new Date().getTime() / 1000;
        backUpAllFiles(new File(source.getRootPath()), source, backupTarget, filePathAndIdMap, sta, "", backupTask.getId(),backupTask.getCreateTime());

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
     * 将一个 父文件夹 的所有数据备份到目标目录中
     *
     * @param fatherFile
     * @param backupSource
     * @param backupTarget
     * @param filePathAndIdMap
     * @param statistic
     * @param middlePath
     */
    private void backUpAllFiles(File fatherFile, BackupSource backupSource, BackupTarget backupTarget,
                                Map<String, Long> filePathAndIdMap, Statistic statistic, String middlePath,
                                Long backupTaskId,Date taskBackupStartTime) {
        File[] fileArr = fatherFile.listFiles();
        for (File file : fileArr) {
//            if (file.toString().indexOf("/.") != -1 || file.toString().indexOf("\\.") != -1) {
//                continue;
//            }
            if (file.isDirectory()) {
                // --if-- 若是目录，先在目标目录下创建目录，然后递归备份文件
                String targetName = getTargetPath(file, backupTarget, middlePath);
                File targetFile = new File(targetName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                backUpAllFiles(file, backupSource, backupTarget, filePathAndIdMap, statistic,
                        middlePath + file.getName() + File.separator, backupTaskId,taskBackupStartTime);
            }
            if (file.isFile()) {
                // --if-- 若是文件，执行备份操作
                try {
                    execBackUp(backupSource, backupTarget, file.toString(), filePathAndIdMap,
                            statistic, middlePath, backupTaskId,taskBackupStartTime);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
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
    private void execBackUp(BackupSource source, BackupTarget target, String backupSourceFilePath,
                            Map<String, Long> filePathAndIdMap, Statistic statistic, String middlePath,
                            Long backupTaskId,Date taskBackupStartTime) throws SQLException, IOException {
        System.out.println("执行execBackUp");
        // todo 检查这里是否合理
       /* if (backupSourceFilePath.indexOf("/.") != -1 || backupSourceFilePath.indexOf("\\.") != -1) {
            // 不拷贝.开头的文件夹和文件
            return;
        }*/
        File backupSourceFile = new File(backupSourceFilePath);
        long fileId;
        if (!filePathAndIdMap.containsKey(backupSourceFilePath)) {
            // --if-- 文件还没有备份过，将其插入到数据库中，并取出id

            // 第一次拷贝该文件，将文件信息存储到数据库中
            BackupFile backupFile = new BackupFile();
            backupFile.setBackupSourceId(source.getId());
            backupFile.setBackupTargetId(target.getId());
            backupFile.setFilePath(backupSourceFilePath.replace("\\", "\\\\").replace("'", "\\'"));
            backupFile.setBackupNum(0);
            backupFile.setLastBackupTime(new DateTime());
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
            if (!execBackupSingleFile(backupSourceFilePath, target, middlePath, fileId, backupTaskId)) {
                log.error("备份出错");
                return;
            } else {
                // 更新文件的备份次数
                backupFileService.updateBackupNum(fileId);
            }
        }

        // 每隔一秒输出一下拷贝进度
        System.out.println("更新表格");
        statistic.finishBackupFileNum++;
        statistic.finishBackupByteNum += backupSourceFile.length();
        long curTime = new Date().getTime();
        if (curTime != statistic.timestamp) {
            statistic.timestamp = curTime / 1000;
            log.info("文件数量：拷贝进度:" + statistic.finishBackupFileNum * 100.0 / statistic.totalBackupFileNum + "%  " + statistic.finishBackupFileNum + "/" + statistic.totalBackupFileNum +
                    "； 文件大小：拷贝进度:" + statistic.finishBackupByteNum * 100.0 / statistic.totalBackupByteNum + "%  " + statistic.finishBackupByteNum + "/" + statistic.totalBackupByteNum);
            BackupTask backupTask = new BackupTask();
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

    /**
     * 执行 单个文件 的拷贝
     *
     * @param sourceFilePath 需要备份的源文件路径
     * @param target         备份的目标目录
     * @param middlePath     中间路径
     * @param backupFileId   存储到数据库中的备份文件ID
     * @return
     * @throws IOException
     */
    private boolean execBackupSingleFile(String sourceFilePath, BackupTarget target, String middlePath, long backupFileId, long backupTaskId) throws IOException {
        Date start = new Date();
        File backupSourceFile = new File(sourceFilePath);
        FileInputStream sourceFileStream = new FileInputStream(backupSourceFile);
        String md5str;
        try {
            md5str = DigestUtil.md5Hex(sourceFileStream);
        } finally {
            sourceFileStream.close();
        }
        // 目标路径名称
        String targetFilePath = getTargetPath(backupSourceFile, target, middlePath);
        try {
            log.info("备份文件，从" + sourceFilePath + "到" + targetFilePath);
            backupWithFileChannel(backupSourceFile, new File(targetFilePath));
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
        history.setBackupTargetRootId(target.getBackupSourceId());
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
        List<BackupTarget> backupSourceList = backupTargetService.list(new QueryWrapper<BackupTarget>().eq("backup_source_id", source.getId()));
        if (backupSourceList.size() == 0) {
            throw new ClientException("没有为 备份源 配置 备份目标目录，请先配置 备份目标目录");
        }
        for (BackupTarget backupTarget : backupSourceList) {
            File file = new File(backupTarget.getTargetRootPath());
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
                if (!mkdir) {
                    throw new ServiceException("目标目录创建失败");
                }
            }
        }
        List<Task> taskList = backupSourceList.stream().map(item -> {
            return new Task(source, item);
        }).toList();
        return taskList;
    }
}
