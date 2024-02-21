//package org.dam.service.impl;
//
//import cn.hutool.core.date.DateTime;
//import cn.hutool.crypto.digest.DigestUtil;
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import lombok.extern.slf4j.Slf4j;
//import org.dam.cache.Cache;
//import org.dam.common.constant.SystemConstant;
//import org.dam.common.constant.SystemParamEnum;
//import org.dam.common.exception.ClientException;
//import org.dam.common.exception.ServiceException;
//import org.dam.common.page.PageResponse;
//import org.dam.common.utils.FileUtils;
//import org.dam.common.utils.compress.GzipCompressUtil;
//import org.dam.controller.BackupController;
//import org.dam.controller.WebSocketServer;
//import org.dam.entity.*;
//import org.dam.entity.request.BackupFileRequest;
//import org.dam.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.channels.FileChannel;
//import java.sql.SQLException;
//import java.util.*;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.stream.Collectors;
//
//import static org.dam.service.impl.BackupTaskServiceImpl.setProgress;
//
///**
// * @Author dam
// * @create 2024/1/19 13:53
// */
//@Slf4j
//@Service
//public class BackupServiceImpl1 implements BackupService {
//
//    @Autowired
//    private BackupFileHistoryService backupFileHistoryService;
//    @Autowired
//    private BackupFileService backupFileService;
//    @Autowired
//    private BackupSourceService backupSourceService;
//    @Autowired
//    private BackupTargetService backupTargetService;
//    @Autowired
//    private ThreadPoolExecutor executor;
//    @Autowired
//    private BackupTaskService backupTaskService;
//    @Autowired
//    private WebSocketServer webSocketServer;
//    @Autowired
//    private BackupController backupController;
//    @Autowired
//    private SysParamService sysParamService;
//
//    private Random random = new Random();
//
//    /**
//     * 对指定的备份源进行备份
//     *
//     * @param sourceId
//     */
//    @Override
//    public void backupBySourceId(Long sourceId, List<Task> taskList) {
//        // 更新数据源备份次数
//        backupSourceService.updateBackupNum(sourceId);
//        // 查询忽略文件和忽略目录
//        List<String> ignoreFileList = sysParamService.getIgnoreFileOrIgnoreDir(SystemParamEnum.IGNORE_FILE_NAME.getParamName());
//        List<String> ignoreDirectoryList = sysParamService.getIgnoreFileOrIgnoreDir(SystemParamEnum.IGNORE_DIRECTORY_NAME.getParamName());
//        // 执行备份
//        CompletableFuture[] futureArr = new CompletableFuture[taskList.size()];
//        for (int i = 0; i < taskList.size(); i++) {
//            int finalI = i;
//            Task task = taskList.get(finalI);
//            // 是否记录文件信息
//            boolean isRecordBackupFile;
//            if (i > 0) {
//                // 如果是集中备份到多个目标目录中，只记录一次文件信息
//                isRecordBackupFile = false;
//            } else {
////                isRecordBackupFile = true;
//                // 如果是分散备份，或者是压缩备份，需要记录文件结构信息
//                isRecordBackupFile = task.getSource().getBackupType() == 1 || task.getSource().getIsCompress() == 1;
//            }
//            boolean finalIsRecordBackupFile = isRecordBackupFile;
//            backUpByTask(task, finalIsRecordBackupFile, ignoreFileList, ignoreDirectoryList);
////            futureArr[i] = CompletableFuture.runAsync(() -> backUpByTask(task, finalIsRecordBackupFile, ignoreFileList, ignoreDirectoryList), executor).exceptionally(e -> {
////                log.error(e.getMessage());
////                // 备份失败（出现异常），移除相应数据源ID
////                if (backupController.sourceIDSet.contains(sourceId)) {
////                    backupController.sourceIDSet.remove(sourceId);
////                }
////                Map<String, Object> dataMap = new HashMap<>();
////                dataMap.put("content", "任务备份失败");
////                dataMap.put("message", e.getMessage());
////                webSocketServer.sendMessage(JSON.toJSONString(dataMap), WebSocketServer.usernameAndSessionMap.get("Admin"));
////                return null;
////            });
//        }
////        CompletableFuture.allOf(futureArr).join();
//
//        // 备份完成，移除相应数据源ID
//        if (backupController.sourceIDSet.contains(sourceId)) {
//            backupController.sourceIDSet.remove(sourceId);
//        }
//    }
//
//    /**
//     * 检查数据，删除 无效备份信息 和 已备份文件
//     * 什么叫无效？简单来说就是，已备份文件和原文件对应不上，或者说原文件被删除了
//     *
//     * @param sourceId
//     */
//    @Override
//    public void clearBySourceId(Long sourceId) {
//        long current = 1;
//        // 存储要删除的文件
//        List<Long> removeBackupFileIdList = new ArrayList<>();
//        List<String> removeBackupTargetFilePathList = new ArrayList<>();
//        BackupFileRequest backupFileRequest = new BackupFileRequest();
//        while (true) {
//            // 分页查询出数据，即分批检查，避免数据量太大，占用太多内存
//            backupFileRequest.setCurrent(current);
//            backupFileRequest.setSize(1000L);
//            backupFileRequest.setBackupSourceId(sourceId);
//            PageResponse<BackupFile> backupFilePageResponse = backupFileService.pageBackupFile(backupFileRequest);
//
//            if (backupFilePageResponse.getRecords().size() > 0) {
//                for (BackupFile backupFile : backupFilePageResponse.getRecords()) {
//                    // 获取备份文件的路径
//                    // todo 待优化为存储的时候，不存储整一个路径，节省数据库空间，只存储从根目录开始后面的路径，后面获取整个路径再进行拼接
//                    String sourceFilePath = backupFile.getSourceFilePath();
//                    File sourceFile = new File(sourceFilePath);
//                    if (!sourceFile.exists()) {
//                        // --if-- 如果原目录该文件已经被删除，则删除
//                        removeBackupFileIdList.add(backupFile.getId());
//                        removeBackupTargetFilePathList.add(backupFile.getTargetFilePath());
//                    }
//                }
//                // 换一页来检查
//                current += 1;
//            } else {
//                // 查不出数据了，说明检查完了
//                break;
//            }
//        }
//
//        if (removeBackupFileIdList.size() > 0) {
//            // 批量删除无效备份文件
//            backupFileService.removeByIds(removeBackupFileIdList);
//            // 删除无效的已备份文件
//            for (String backupTargetFilePath : removeBackupTargetFilePathList) {
//                File removeFile = new File(backupTargetFilePath);
//                if (removeFile.exists()) {
//                    boolean delete = FileUtils.recursionDeleteFiles(removeFile);
//                    if (!delete) {
//                        throw new ServiceException("文件无法删除");
//                    }
//                }
//            }
//            // 批量删除无效备份文件对应的备份记录
//            backupFileHistoryService.removeByFileIds(removeBackupFileIdList);
//        }
//    }
//
//    /**
//     * 根据备份任务来进行备份
//     *
//     * @param task                备份任务
//     * @param isRecordBackupFile  是否记录文件信息
//     * @param ignoreFileList      忽略文件名列表
//     * @param ignoreDirectoryList 忽略目录名列表
//     */
//    private void backUpByTask(Task task, boolean isRecordBackupFile,
//                              List<String> ignoreFileList, List<String> ignoreDirectoryList) {
//        BackupSource backupSource = task.getSource();
//        BackupTarget backupTarget = task.getTarget();
//        // 找到备份目录下面的所有文件
//        Statistic sta = new Statistic(0, 0, 0, 0, new Date().getTime() / 1000);
//        // 获取数据源的统计数据
//        getStatisticMessage(new File(backupSource.getRootPath()), sta);
////        log.info("当前数据源（id={}）下的总文件数量:{}，总字节数：{}", backupSource.getId(), sta.totalBackupFileNum, sta.totalBackupByteNum);
//        String targetRootPath = getTargetRootPath(task, backupSource, backupTarget);
//        // 将任务插入到数据库中
//        BackupTask backupTask = new BackupTask(backupSource.getRootPath(), targetRootPath,
//                sta.totalBackupFileNum, 0, sta.totalBackupByteNum, 0L,
//                0, "0.0", "0.0", 0L, new Date());
//        backupTaskService.save(backupTask);
////        log.info("发送任务消息，通知前端任务创建成功");
//        Map<String, Object> dataMap = new HashMap<>();
//        dataMap.put("content", "任务创建成功");
//        dataMap.put("backupTask", backupTask);
//        webSocketServer.sendMessage(JSON.toJSONString(dataMap), WebSocketServer.usernameAndSessionMap.get("Admin"));
////        log.info("任务创建成功，开始备份");
//
//        /// 查询出数据源和备份目标对应的 备份文件信息
//        // 查询出当前数据源中所有已经备份过的文件
//        QueryWrapper<BackupFile> backupFileQueryWrapper = new QueryWrapper<BackupFile>()
//                .eq("backup_source_id", backupSource.getId())
//                .eq("father_id", 0L)
//                .select("id", "source_file_path","file_name");
//        if (backupSource.getBackupType() == 0) {
//            // 集中备份的时候，根据目标id查询；分散备份的时候，目标id不确定，所以都查询出来
//            backupFileQueryWrapper.eq("backup_target_id", backupTarget.getId());
//        }
//        List<BackupFile> backupFileList = backupFileService.list(backupFileQueryWrapper);
//
//        // 将数据源的数据备份到多个目标目录下面
//        sta.second = new Date().getTime() / 1000;
//
//        /// 开始备份
//        backUpAllFilesOfFatherFile(task, new File(backupSource.getRootPath()),
//                backupSource, backupTarget, task.getTargetList(), sta,
//                "", backupTask.getId(), backupTask.getCreateTime(),
//                0L, backupFileList, ignoreFileList, ignoreDirectoryList);
//
//        /// 备份结束
//        if (Cache.STOP_TASK_ID_SET.contains(backupTask.getId())) {
//            // --if-- 因为备份任务被暂停才结束的
//            Cache.STOP_TASK_ID_SET.remove(backupTask.getId());
//        } else {
//            // --if-- 备份完成了，修改备份任务的状态为完成
//            backupTask.setBackupStatus(2);
//            backupTask.setFinishFileNum(sta.getTotalBackupFileNum());
//            backupTask.setFinishByteNum(sta.getTotalBackupByteNum());
//            backupTask.setEndTime(new Date());
//            backupTask.setBackupTime(backupTask.getEndTime().getTime() - backupTask.getCreateTime().getTime());
//            backupTaskService.updateById(backupTask);
//            setProgress(backupTask);
////            log.info("发送任务消息，通知前端任务备份完成");
//            dataMap = new HashMap<>();
//            dataMap.put("content", "任务备份完成");
//            dataMap.put("backupTask", backupTask);
//            webSocketServer.sendMessage(JSON.toJSONString(dataMap), WebSocketServer.usernameAndSessionMap.get("Admin"));
//        }
//    }
//
//    /**
//     * 获取备份目标目录
//     *
//     * @param task
//     * @param backupSource
//     * @param target
//     * @return
//     */
//    private static String getTargetRootPath(Task task, BackupSource backupSource, BackupTarget target) {
//        String targetRootPath;
//        if (backupSource.getBackupType() == 1) {
//            // --if-- 分散备份，将所有备份目标的根路径一起统计起来
//            targetRootPath = "分散备份：\n";
//            for (BackupTarget backupTarget : task.getTargetList()) {
//                targetRootPath += backupTarget.getTargetRootPath() + "\n";
//            }
//        } else {
//            targetRootPath = target.getTargetRootPath();
//        }
//        return targetRootPath;
//    }
//
//    /**
//     * 将一个 父文件夹 的所有文件 备份到 目标目录中
//     *
//     * @param fatherFile
//     * @param backupSource
//     * @param backupTarget
//     * @param statistic
//     * @param middlePath
//     */
//    private void backUpAllFilesOfFatherFile(Task task, File fatherFile,
//                                            BackupSource backupSource, BackupTarget backupTarget, List<BackupTarget> targetList,
//                                            Statistic statistic, String middlePath,
//                                            Long backupTaskId, Date taskBackupStartTime,
//                                            Long fatherId, List<BackupFile> backupFileList,
//                                            List<String> ignoreFileList, List<String> ignoreDirectoryList) {
//        File[] sonFileArr = fatherFile.listFiles();
//        HashMap<String, BackupFile> fileNameAndBackupFileMap = new HashMap<>();
//        HashMap<Long, BackupFileHistory> fileIdAndFileHistoryMap = new HashMap<>();
//        if (backupFileList != null) {
//            // 记录要移除的 文件信息ID
//            List<Long> removeBackupFileIdList = new ArrayList<>();
//            // 存储数据源中存在的文件的名称
//            HashSet<String> fileNameSet = new HashSet<>();
//            for (int i = 0; i < sonFileArr.length; i++) {
//                fileNameSet.add(sonFileArr[i].getName());
//            }
//            List<Long> exitBackupFileIdList = new ArrayList<>();
//            for (BackupFile backupFile : backupFileList) {
//                fileNameAndBackupFileMap.put(backupFile.getFileName(), backupFile);
//                if (!fileNameSet.contains(backupFile.getFileName())) {
//                    removeBackupFileIdList.add(backupFile.getId());
//                } else {
//                    exitBackupFileIdList.add(backupFile.getId());
//                }
//            }
//            // 如果数据源中没有相应文件，将其也从数据库中删除
//            backupFileService.recursionRemoveBackupFile(removeBackupFileIdList);
//
//            /// 查询相关备份文件的备份历史
//            if (exitBackupFileIdList.size() > 0) {
//                List<BackupFileHistory> historyList = backupFileHistoryService.listLastBackupHistoryByBackupFileIdList(exitBackupFileIdList);
//                for (BackupFileHistory fileHistory : historyList) {
//                    fileIdAndFileHistoryMap.put(fileHistory.getBackupFileId(), fileHistory);
//                }
//            }
//        }
//        for (File file : sonFileArr) {
//            if (Cache.STOP_TASK_ID_SET.contains(backupTaskId)) {
//                // --if-- 如果任务被暂停，退出备份，存储当前备份任务的信息
//                BackupTask backupTask = new BackupTask();
//                backupTask.setId(backupTaskId);
//                backupTask.setBackupStatus(4);
//                backupTask.setFinishFileNum(statistic.getFinishBackupFileNum());
//                backupTask.setFinishByteNum(statistic.getFinishBackupByteNum());
//                backupTask.setEndTime(new Date());
//                backupTask.setBackupTime(backupTask.getEndTime().getTime() - backupTask.getCreateTime().getTime());
//                backupTaskService.updateById(backupTask);
//                backupTask.setTotalFileNum(statistic.getTotalBackupFileNum());
//                backupTask.setTotalByteNum(statistic.getTotalBackupByteNum());
//                setProgress(backupTask);
//                backupTask.setBackupSourceRoot(backupSource.getRootPath());
//                backupTask.setBackupTargetRoot(backupTarget.getTargetRootPath());
//                backupTask.setCreateTime(taskBackupStartTime);
////                log.info("发送任务消息，通知前端任务暂停");
//                Map<String, Object> dataMap = new HashMap<>();
//                dataMap.put("content", "任务暂停备份");
//                dataMap.put("backupTask", backupTask);
//                webSocketServer.sendMessage(JSON.toJSONString(dataMap), WebSocketServer.usernameAndSessionMap.get("Admin"));
//                break;
//            }
////            if (file.toString().indexOf("/.") != -1 || file.toString().indexOf("\\.") != -1) {
////                continue;
////            }
//            if (file.isDirectory()) {
//                // --if-- 若是目录，先在目标目录下创建目录，然后递归备份文件
//                if (isContainedInIgnoreList(ignoreDirectoryList, file)) {
//                    continue;
//                }
//                String targetFilePath = getTargetFilePath(backupSource, backupTarget, targetList, middlePath, file);
//                // 查询备份文件数据表是否已经包含这个记录
//                BackupFile backupFile = fileNameAndBackupFileMap.get(file.getName());
//                Long curBackupFileId = backupFile == null ? null : backupFile.getId();
//
//                File targetFile = new File(targetFilePath);
//                if (!targetFile.exists()) {
//                    boolean mkdirs = targetFile.mkdirs();
//                    if (mkdirs) {
//                        // 将目录插入到数据库中
//                        if (curBackupFileId == null) {
//                            curBackupFileId = saveBackupFileDir(backupSource, backupTarget, targetFilePath, fatherId, file);
//                        }
//                    } else {
//                        throw new ServiceException("无法创建目录，可能是权限不够");
//                    }
//                } else {
//                    // --if-- 虽然目录已经存在，但是数据库中没有信息，还是需要存储相关信息
//                    if (curBackupFileId == null) {
//                        curBackupFileId = saveBackupFileDir(backupSource, backupTarget, targetFilePath, fatherId, file);
//                    }
//                }
//
//                // 是否存在对应的文件信息，如果备份类型不是是分散存储，那么文件信息肯定不存在
//                boolean haveBackupFile = fileNameAndBackupFileMap.get(file.getName()) != null;
//                List<BackupFile> children = null;
//                if (haveBackupFile) {
//                    children = new ArrayList<>();
//                    children.addAll(backupFileService.list(new QueryWrapper<BackupFile>().
//                            eq("backup_source_id", backupSource.getId()).
//                            eq("father_id", curBackupFileId)));
//                }
//                backUpAllFilesOfFatherFile(task, file, backupSource, backupTarget,
//                        targetList, statistic,
//                        middlePath + file.getName() + File.separator, backupTaskId, taskBackupStartTime,
//                        curBackupFileId, children,
//                        ignoreFileList, ignoreDirectoryList);
//            } else {
//                // --if-- 若是文件，执行备份操作
//                if (isContainedInIgnoreList(ignoreFileList, file)) {
//                    continue;
//                }
//                if (file.getName().contains(".DS_Store")) {
//                    // 跳过Macos的Finder创建文件
//                    continue;
//                }
//                try {
//                    execSingleFileBackUp(task, backupSource, backupTarget, targetList, file.toString(),
//                            statistic, middlePath, backupTaskId, taskBackupStartTime, fatherId,
//                            fileNameAndBackupFileMap, fileIdAndFileHistoryMap);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
//
//    /**
//     * 判断 文件或文件夹 在备份时是否被忽略
//     *
//     * @param ignoreDirectoryList
//     * @param file
//     * @return
//     */
//    private static boolean isContainedInIgnoreList(List<String> ignoreDirectoryList, File file) {
//        for (String ignoreDirectory : ignoreDirectoryList) {
//            if (file.getName().equals(ignoreDirectory)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 存储备份文件夹信息
//     *
//     * @param backupSource
//     * @param backupTarget
//     * @param sourceFilePath
//     * @param targetFilePath
//     */
//    private Long saveBackupFileDir(BackupSource backupSource, BackupTarget backupTarget, String targetFilePath,
//                                   Long fatherId, File sourceFile) {
//        BackupFile backupFile = new BackupFile();
//        backupFile.setBackupSourceId(backupSource.getId());
//        if (backupSource.getBackupType() == 0) {
//            backupFile.setBackupTargetId(backupTarget.getId());
//        } else {
//            backupFile.setBackupTargetId(0L);
//        }
//        backupFile.setTargetFilePath(targetFilePath);
//        backupFile.setBackupNum(0);
//        backupFile.setLastBackupTime(new DateTime());
//        backupFile.setFileType(0);
//        backupFile.setFileName(sourceFile.getName());
//        backupFile.setSourceFilePath(sourceFile.getPath());
//        // 目录的后缀为空
//        backupFile.setFileSuffix(null);
//        backupFile.setFileLength(sourceFile.length());
//        backupFile.setFileLengthAfterCompress(0L);
//        backupFile.setFatherId(fatherId);
//        backupFile.setIsCompress(backupSource.getIsCompress());
//        backupFile.setIsContainFile(sourceFile.listFiles().length > 0 ? 1 : 0);
//        backupFileService.save(backupFile);
//        return backupFile.getId();
//    }
//
//    /**
//     * 执行一个文件的备份
//     * 首先判断文件是否已经备份或者是否有所修改，是则进行备份
//     *
//     * @param source
//     * @param target
//     * @param backupSourceFilePath
//     * @param statistic
//     * @param middlePath
//     * @throws SQLException
//     * @throws IOException
//     */
//    private void execSingleFileBackUp(Task task, BackupSource source, BackupTarget target,
//                                      List<BackupTarget> targetList, String backupSourceFilePath,
//                                      Statistic statistic, String middlePath,
//                                      Long backupTaskId, Date taskBackupStartTime,
//                                      Long fatherId, HashMap<String, BackupFile> fileNameAndBackupFileMap,
//                                      HashMap<Long, BackupFileHistory> fileIdAndFileHistoryMap) throws SQLException, IOException {
//       /* if (backupSourceFilePath.indexOf("/.") != -1 || backupSourceFilePath.indexOf("\\.") != -1) {
//            // 不拷贝.开头的文件夹和文件
//            return;
//        }*/
//        // 获取源文件
//        File backupSourceFile = new File(backupSourceFilePath);
//        // 获取备份目标路径
//        String targetFilePath = getTargetFilePath(source, target, targetList, middlePath, backupSourceFile);
//
//        int isCompress = 0;
//        if (source.getIsCompress() == 1 && backupSourceFile.length() <= 10 * 1024 * 1024) {
//            // --if-- 当数据源设置了压缩，且文件的大小等于10M才进行压缩
//            isCompress = 1;
//            if (targetFilePath.lastIndexOf(".") == -1) {
//                // --if-- 文件本身无后缀，直接添加压缩后缀
//                targetFilePath += SystemConstant.ZIP_SUFFIX;
//            } else {
//                // --if-- 文件本身有后缀，则删除原后缀，再换上压缩后缀
//                targetFilePath = targetFilePath.substring(0, targetFilePath.lastIndexOf(".")) + SystemConstant.ZIP_SUFFIX;
//            }
//        }
//
//        long backFileIdInDataBase;
//        long targetId = source.getBackupType() == 0 ? target.getId() : 0;
//        int backupNum = 1;
//        if (fileNameAndBackupFileMap.get(backupSourceFile.getName()) == null) {
//            // --if-- 文件还没有备份过，将其插入到数据库中，并取出id
//            BackupFile backupFile = insertBackupFile(source, backupSourceFilePath, targetFilePath, targetId,
//                    fatherId, isCompress, backupSourceFile);
//            // 查询出其在数据库中对应的ID
//            backFileIdInDataBase = backupFile.getId();
//        } else {
//            // 直接从字典中获取
//            BackupFile backupFileInDatabase = fileNameAndBackupFileMap.get(backupSourceFile.getName());
//            backFileIdInDataBase = backupFileInDatabase.getId();
//            backupNum = backupFileInDatabase.getBackupNum();
//        }
//        // 查询出该文件的最后一次的备份历史
////        BackupFileHistory fileHistory = backupFileHistoryService.getLastBackupHistory(backFileIdInDataBase);
//        BackupFileHistory fileHistory = fileIdAndFileHistoryMap.get(backFileIdInDataBase);
//        // 判断文件是否有修改
//        boolean isNeedBackup = true;
//        if (fileHistory != null) {
//            long lastModify = fileHistory.getModifyTime();
//            long fileSize = fileHistory.getFileSize();
//            String historyMD5 = fileHistory.getMd5();
//            if (lastModify == backupSourceFile.lastModified() && fileSize == backupSourceFile.length()) {
//                // 如果文件的 修改时间 和 文件大小 都和数据库中的对应，认为文件没有被修改，无需备份
//                isNeedBackup = false;
//            }
//            // 如果修改时间不一样，文件大小一样，追加校验一次hash，如果hash一样，则更新修改时间，不执行备份
//            if (lastModify != backupSourceFile.lastModified() && fileSize == backupSourceFile.length()) {
//                // 只要输入一样，输出的MD5码就是一样的，如果md5一样，不执行备份
//                String md5str = DigestUtil.md5Hex(new FileInputStream(backupSourceFile));
//                if (md5str.equals(historyMD5)) {
//                    isNeedBackup = false;
//                }
//            }
//        }
//        // 执行具体的备份
////        System.out.println("开始执行备份");
//        if (isNeedBackup) {
//            if (!execBackupSingleFile(isCompress, backupSourceFilePath, source.getId(), targetId, targetFilePath, backFileIdInDataBase, backupTaskId)) {
//                log.error("备份出错");
//                return;
//            } else {
//                /// 更新文件信息
//                BackupFile newBackupFile = new BackupFile();
//                // 文件的大小可能会改变
//                newBackupFile.setFileLength(backupSourceFile.length());
//                // 文件大小改变之后，压缩之后的文件大小也会改变
//                if (isCompress == 1) {
//                    File targetFile = new File(targetFilePath);
//                    newBackupFile.setFileLengthAfterCompress(targetFile.length());
//                }
//                // 本来可以压缩的文件，修改之后可能不再可以压缩，因为空间可能变大
//                newBackupFile.setIsCompress(isCompress);
//                // 更新文件的备份次数
//                newBackupFile.setBackupNum(++backupNum);
//                // 修改文件的上次备份时间
//                newBackupFile.setLastBackupTime(new Date());
//                backupFileService.updateById(newBackupFile);
//            }
//        }
//
//        // 每隔一秒输出一下拷贝进度
//        statistic.finishBackupFileNum++;
//        statistic.finishBackupByteNum += backupSourceFile.length();
//        long curTime = System.currentTimeMillis();
//        if ((curTime / 1000) != statistic.second) {
//            statistic.second = curTime / 1000;
////            log.info("文件数量：拷贝进度:" + statistic.finishBackupFileNum * 100.0 / statistic.totalBackupFileNum + "%  " + statistic.finishBackupFileNum + "/" + statistic.totalBackupFileNum +
////                    "； 文件大小：拷贝进度:" + statistic.finishBackupByteNum * 100.0 / statistic.totalBackupByteNum + "%  " + statistic.finishBackupByteNum + "/" + statistic.totalBackupByteNum);
//            BackupTask backupTask = new BackupTask();
//            backupTask.setId(backupTaskId);
//            backupTask.setBackupStatus(1);
//            backupTask.setFinishFileNum(statistic.finishBackupFileNum);
//            backupTask.setFinishByteNum(statistic.finishBackupByteNum);
//            backupTask.setBackupTime(curTime - taskBackupStartTime.getTime());
//            backupTaskService.updateById(backupTask);
//            // 剩下的信息用来给前端看的，不需要更新到数据库中
//            backupTask.setBackupSourceRoot(source.getRootPath());
//            backupTask.setBackupTargetRoot(getTargetRootPath(task, source, target));
//            backupTask.setTotalFileNum(statistic.totalBackupFileNum);
//            backupTask.setTotalByteNum(statistic.totalBackupByteNum);
//            backupTask.setCreateTime(taskBackupStartTime);
//            setProgress(backupTask);
////            log.info("发送任务消息，通知前端备份进度变化");
//            Map<String, Object> dataMap = new HashMap<>();
//            dataMap.put("content", "备份进度变化");
//            dataMap.put("backupTask", backupTask);
//            webSocketServer.sendMessage(JSON.toJSONString(dataMap), WebSocketServer.usernameAndSessionMap.get("Admin"));
//        }
//
//    }
//
//    /**
//     * 将备份文件存储到数据库中
//     *
//     * @param source
//     * @param backupSourceFilePath
//     * @param targetFilePath
//     * @param targetId
//     * @return
//     */
//    private BackupFile insertBackupFile(BackupSource source, String backupSourceFilePath, String targetFilePath, long targetId,
//                                        Long fatherId, int isCompress, File backupSourceFile) {
//        BackupFile backupFile = new BackupFile();
//        backupFile.setBackupSourceId(source.getId());
//        backupFile.setBackupTargetId(targetId);
//        backupFile.setSourceFilePath(backupSourceFilePath.replace("\\", "\\\\").replace("'", "\\'"));
//        backupFile.setTargetFilePath(targetFilePath);
//        backupFile.setBackupNum(1);
//        backupFile.setLastBackupTime(new DateTime());
//        backupFile.setFileType(1);
//        backupFile.setFileName(backupSourceFile.getName());
//        // 分散存储的时候，不会创建文件夹，因此直接为空就行
//        // 目录的后缀为空
//        if (backupSourceFile.getName().contains(".")) {
//            backupFile.setFileSuffix(backupSourceFile.getName().substring(backupSourceFile.getName().lastIndexOf(".")));
//        } else {
//            backupFile.setFileSuffix("该文件无后缀");
//        }
//        backupFile.setFileLength(backupSourceFile.length());
//        backupFile.setFatherId(fatherId);
//        backupFile.setIsCompress(isCompress);
//        backupFile.setFileType(1);
//        backupFile.setIsContainFile(0);
//        backupFileService.save(backupFile);
//        return backupFile;
//    }
//
//    /**
//     * 组装完整的备份目标路径
//     *
//     * @param source
//     * @param target
//     * @param targetList
//     * @param middlePath
//     * @param backupSourceFile
//     * @return
//     */
//    private String getTargetFilePath(BackupSource source, BackupTarget target, List<BackupTarget> targetList, String middlePath, File backupSourceFile) {
//        String targetFilePath;
//        if (source.getBackupType() == 0) {
//            // --if-- 集中备份
//            targetFilePath = getTargetPath(backupSourceFile, target, middlePath);
//        } else {
//            // --if-- 分散备份
//            // 获取一个备份目标目录
//            BackupTarget bestBackupTarget = getBestBackupTarget(backupSourceFile.length(), targetList);
//            targetFilePath = bestBackupTarget.getTargetRootPath() + File.separator + backupSourceFile.getName();
//        }
//        return targetFilePath;
//    }
//
//    /**
//     * 执行 单个文件 的拷贝
//     *
//     * @param isCompress     是否压缩
//     * @param sourceFilePath 需要备份的源文件路径
//     * @param targetId       备份的目标目录ID
//     * @param targetFilePath 备份的目标文件路径
//     * @param backupFileId   存储到数据库中的备份文件ID
//     * @return
//     * @throws IOException
//     */
//    private boolean execBackupSingleFile(int isCompress, String sourceFilePath, Long sourceId, Long targetId,
//                                         String targetFilePath, Long backupFileId, Long backupTaskId) throws IOException {
//        Date start = new Date();
//        File backupSourceFile = new File(sourceFilePath);
//        FileInputStream sourceFileStream = new FileInputStream(backupSourceFile);
//        String md5str;
//        try {
//            md5str = DigestUtil.md5Hex(sourceFileStream);
//        } finally {
//            sourceFileStream.close();
//        }
//
//        try {
//            if (isCompress == 1) {
//                // 对文件进行压缩
//                GzipCompressUtil.compressFile(backupSourceFile, targetFilePath);
//            } else {
//                // 直接拷贝
//                backupWithFileChannel(backupSourceFile, new File(targetFilePath));
//            }
////            log.info("备份文件成功，从" + sourceFilePath + " 到 " + targetFilePath);
//        } catch (Exception e) {
////            log.info("备份文件失败，从" + sourceFilePath + " 到 " + targetFilePath);
//            return false;
//        }
//        // 保存文件备份历史
//        BackupFileHistory history = new BackupFileHistory();
//        history.setBackupSourceId(sourceId);
//        history.setBackupFileId(backupFileId);
//        history.setModifyTime(backupSourceFile.lastModified());
//        history.setFileSize(backupSourceFile.length());
//        history.setBackupStartTime(start);
//        history.setBackupEndTime(new Date());
//        history.setBackupSourceFilePath(sourceFilePath);
//        history.setBackupTargetFilePath(targetFilePath);
//        history.setBackupTargetRootId(targetId);
//        history.setMd5(md5str);
//        history.setBackupTaskId(backupTaskId);
//        backupFileHistoryService.save(history);
//        return true;
//    }
//
//    /**
//     * 将 source 备份到 target
//     *
//     * @param source
//     * @param target
//     * @throws IOException
//     */
//    private static void backupWithFileChannel(File source, File target) throws IOException {
//        FileChannel inputChannel = null;
//        FileChannel outputChannel = null;
//        try {
//            inputChannel = new FileInputStream(source).getChannel();
//            outputChannel = new FileOutputStream(target).getChannel();
//            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (inputChannel != null) {
//                inputChannel.close();
//            }
//            if (outputChannel != null) {
//                outputChannel.close();
//            }
//        }
//    }
//
//
//    /**
//     * 获取完整的目标文件路径
//     *
//     * @param file
//     * @param backupTarget
//     * @param middlePath   层层拼接之后的路径
//     * @return
//     */
//    private static String getTargetPath(File file, BackupTarget backupTarget, String middlePath) {
//        return backupTarget.getTargetRootPath() + File.separator + middlePath + file.getName();
//    }
//
//    /**
//     * 获取一个目录下面的统计信息
//     * 1. 需要备份的文件数量
//     * 2. 需要备份的字节数量
//     *
//     * @param file
//     * @param sta  用来存储统计信息
//     */
//    private void getStatisticMessage(File file, Statistic sta) {
//        File[] fileArr = file.listFiles();
//        for (File f : fileArr) {
//            if (f.isDirectory()) {
//                // --if-- 若是目录，则递归统计该目录下的文件数量
//                getStatisticMessage(f, sta);
//            } else {
//                // --if-- 若是文件，添加到文件夹中
//                sta.totalBackupFileNum++;
//                sta.totalBackupByteNum += f.length();
//            }
//        }
//    }
//
//    /**
//     * 检查 备份源目录是否存在 和 准备好备份目标目录
//     *
//     * @param sourceId
//     */
//    @Override
//    public List<Task> checkSourceAndTarget(Long sourceId) {
//        BackupSource source = backupSourceService.getById(sourceId);
//        if (source == null) {
//            throw new ClientException("id对应备份源信息不存在于数据库中");
//        }
//        File sourceFile = new File(source.getRootPath());
//        if (!sourceFile.exists()) {
//            throw new ServiceException("备份源目录不存在，请检查备份源是否被删除");
//        }
//        // 查询备份源对应的所有 备份目标目录 准备好相关的目录
//        List<BackupTarget> backupTargetList = backupTargetService.list(new QueryWrapper<BackupTarget>().eq("backup_source_id", source.getId()));
//        if (backupTargetList.size() == 0) {
//            throw new ClientException("没有为 备份源 配置 备份目标目录，请先配置 备份目标目录");
//        }
//        for (BackupTarget backupTarget : backupTargetList) {
//            File file = new File(backupTarget.getTargetRootPath());
//            if (!file.exists()) {
//                boolean mkdir = file.mkdir();
//                if (!mkdir) {
//                    throw new ServiceException("目标目录创建失败");
//                }
//            }
//        }
//        List<Task> taskList = null;
//        if (source.getBackupType() == 0) {
//            taskList = backupTargetList.stream().map(item -> {
//                return new Task(source, item, null);
//            }).collect(Collectors.toList());
//        } else if (source.getBackupType() == 1) {
//            Task task = new Task(source, null, backupTargetList);
//            taskList = new ArrayList<>();
//            taskList.add(task);
//        }
//        return taskList;
//    }
//
//    /**
//     * 获取一个有效的目标目录
//     * 如果有多个目标目录，则选择剩余空间最大的目录
//     *
//     * @param needSpace
//     * @return
//     */
//    private BackupTarget getBestBackupTarget(long needSpace, List<BackupTarget> targetList) {
//        List<BackupTarget> bestTargetList = new ArrayList<>();
//        long maxSpace = 0;
//        // 找到剩余空间足够且最大的那个目录
//        for (BackupTarget backupTarget : targetList) {
//            File file = new File(backupTarget.getTargetRootPath());
//            if (file.getFreeSpace() > needSpace && file.getFreeSpace() > maxSpace) {
//                maxSpace = file.getFreeSpace();
//                bestTargetList.clear();
//                bestTargetList.add(backupTarget);
//            } else if (file.getFreeSpace() > needSpace && file.getFreeSpace() == maxSpace) {
//                bestTargetList.add(backupTarget);
//            }
//        }
//        if (bestTargetList.size() == 0) {
//            throw new ServiceException("所有备份目标目录下面都没有足够的空间，无法再执行备份");
//        }
//        return bestTargetList.get(random.nextInt(bestTargetList.size()));
//    }
//
//}
