package org.dam.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.dam.common.exception.ClientException;
import org.dam.common.exception.ServiceException;
import org.dam.entity.*;
import org.dam.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private TotalBackupService totalBackupService;

    /**
     * 对指定的备份源进行备份
     *
     * @param sourceId
     */
    @Override
    public void backupBySourceId(String sourceId) {
        // 检查 备份源目录是否存在 和 准备好备份目标目录
        BackUpTask backUpTask = checkSourceAndTarget(sourceId);
        // todo 需要记录关于本次备份源的信息
        // 执行备份
        backUpByTask(backUpTask);
    }

    private void backUpByTask(BackUpTask backUpTask) {
        BackupSource source = backUpTask.getSource();

        // 存储需要备份的文件路径，全路径，如/Users/mac/Dev/BackUpTest/dasdasdasd.txt
        List<String> fileList = new ArrayList<>();
        // 找到备份目录下面的所有文件
        int fileNum = getSonFileNum(new File(source.getRootPath()));
        log.info("当前数据源（id={}）需要备份的文件数量:{}", source.getId(), fileNum);
        // 记录每个文件路径及其对应的id，如/Users/mac/Dev/BackUpTest/dasdasdasd.txt=>515351
        Map<String, Long> filePathAndIdMap = new HashMap<>();
        // 查询出当前数据源中所有已经备份过的文件
        List<BackupFile> backupFileList = backupFileService.list(new QueryWrapper<BackupFile>().eq("backup_source_id", source.getId()));
        for (BackupFile backupFile : backupFileList) {
            filePathAndIdMap.put(backupFile.getFilePath(), backupFile.getId());
        }
        for (BackupTarget backupTarget : backUpTask.getTargetList()) {
            Statistic statistic = new Statistic(0, new Date().getTime() / 1000, 0, 0);
            backUpAllFiles(new File(source.getRootPath()), source, backupTarget, filePathAndIdMap, statistic, "", fileNum);
        }

    }

    private void backUpAllFiles(File file, BackupSource backupSource, BackupTarget backupTarget, Map<String, Long> filePathAndIdMap, Statistic statistic, String middlePath, int totalFileNum) {
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.toString().indexOf("/.") != -1 || f.toString().indexOf("\\.") != -1) {
                continue;
            }
            if (f.isDirectory()) {
                // --if-- 若是目录，先创建文件，然后递归创建文件
                String targetName = getTargetPath(f, backupTarget, middlePath);
                File dir = new File(targetName);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                backUpAllFiles(f, backupSource, backupTarget, filePathAndIdMap, statistic, middlePath + f.getName() + File.separator, totalFileNum);
            }
            if (f.isFile()) {
                // --if-- 若是文件，添加到文件夹中
                try {
                    execBackUp(backupSource, backupTarget, f.toString(), filePathAndIdMap, statistic, middlePath, totalFileNum);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void execBackUp(BackupSource backupSource, BackupTarget target, String backupFilePath, Map<String, Long> filePathAndIdMap, Statistic statistic, String middlePath, int totalFileNum) throws SQLException, IOException {
        // todo 检查这里是否合理
        if (backupFilePath.indexOf("/.") != -1 || backupFilePath.indexOf("\\.") != -1) {
            // 不拷贝.开头的文件夹和文件
            return;
        }
        statistic.counter++;
        // 每隔一秒输出一下拷贝进度
        if (new Date().getTime() / 1000 != statistic.timestamp) {
            statistic.timestamp = new Date().getTime() / 1000;
            log.info("拷贝进度:" + statistic.counter * 100 / totalFileNum + "%  " + statistic.counter + "/" + totalFileNum);
        }
        File backupSourceFile = new File(backupFilePath);
        long id;
        if (!filePathAndIdMap.containsKey(backupFilePath)) {
            // --if-- 文件还没有备份过，将其插入到数据库中，并取出id

            // 第一次拷贝该文件，将文件信息存储到数据库中
            BackupFile backupFile = new BackupFile();
            backupFile.setBackupSourceId(backupSource.getId());
            backupFile.setFilePath(backupFilePath.replace("\\", "\\\\").replace("'", "\\'"));
            backupFile.setBackupNum(0);
            backupFile.setLastBackupTime(new DateTime());
            backupFileService.save(backupFile);
            // 查询出其在数据库中对应的ID
            id = backupFile.getId();
        } else {
            id = filePathAndIdMap.get(backupFilePath);
        }
        // 查询出该文件的最后一次的备份历史
        QueryWrapper<BackupFileHistory> historyQueryWrapper = new QueryWrapper<BackupFileHistory>().eq("backup_file_id", id);
        BackupFileHistory fileHistory = backupFileHistoryService.getLastBackupHistory(id);
        // 判断文件是否有修改
        if (fileHistory != null) {
            long lastModify = fileHistory.getModifyTime();
            long fileSize = fileHistory.getFileSize();
            String historyMD5 = fileHistory.getMd5();
            if (lastModify == backupSourceFile.lastModified() && fileSize == backupSourceFile.length()) {
                // 如果文件的 修改时间 和 文件大小 都和数据库中的对应，认为文件没有被修改，无需备份
                return;
            }
            // 如果修改时间不一样，文件大小一样，追加校验一次hash，如果hash一样，则更新修改时间，不执行备份
            if (lastModify != backupSourceFile.lastModified() && fileSize == backupSourceFile.length()) {
                // 只要输入一样，输出的MD5码就是一样的，如果md5一样，不执行备份
                String md5str = DigestUtil.md5Hex(new FileInputStream(backupSourceFile));
                if (md5str.equals(historyMD5)) {
                    return;
                }
            }
        }
        // 执行拷贝
        if (!exeCopy(backupSourceFile, target, backupFilePath, middlePath, id)) {
            log.error("拷贝错误！退出...");
            return;
        }
        // 备份成功，备份的文件数量+1
        statistic.backupFileNum++;
        // 备份的文件字节数 增加
        statistic.backupByteNum += backupSourceFile.length();
    }

    /**
     * 执行拷贝
     *
     * @param backupSourceFile
     * @param file
     * @param backupFileId
     * @return
     * @throws IOException
     */
    private boolean exeCopy(File backupSourceFile, BackupTarget target, String file, String middlePath, long backupFileId) throws IOException {
        Date start = new Date();
        FileInputStream fileStream = new FileInputStream(file);
        String md5str;
        try {
            md5str = DigestUtil.md5Hex(fileStream);
        } finally {
            fileStream.close();
        }
        // 目标路径名称
        String targetName = getTargetPath(backupSourceFile, target, middlePath);
        String targetNameSave = File.separator + backupSourceFile.getName();
        LocalDateTime beginCopySingle = LocalDateTime.now();
        try {
            log.info("备份文件，从" + file + "到" + targetName);
            copyFileUsingFileChannels(backupSourceFile, new File(targetName));
        } catch (Exception e) {
            log.info("备份文件失败，从" + file + " 到 " + targetName);
            return false;
        }
        // 保存备份历史
        BackupFileHistory history = new BackupFileHistory();
        history.setBackupFileId(backupFileId);
        history.setModifyTime(new Date().getTime());
        history.setFileSize(backupSourceFile.length());
        history.setBackupStartTime(start);
        history.setBackupEndTime(new Date());
        history.setBackupTargetPath(targetNameSave);
        history.setBackupTargetRootId(target.getBackupSourceId());
        history.setMd5(md5str);
        return true;
    }

    /**
     * 将source备份到dest
     *
     * @param source
     * @param dest
     * @throws IOException
     */
    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            String path = dest.getPath();
            String filePath = path.substring(0, path.lastIndexOf("/"));
            File file = new File(filePath);
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                if (mkdirs) {
//                    System.out.println("文件创建成功");
                } else {
//                    System.out.println("文件创建不成功");
                }
            } else {
//                System.out.println("dest已经存在");
            }
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
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
        return backupTarget.getTagetRootPath() + File.separator + middlePath + file.getName();
    }

    /**
     * 获取一个目录下面的所有文件数量
     *
     * @param file
     */
    private int getSonFileNum(File file) {
        File[] fs = file.listFiles();
        int fileNum = 0;
        for (File f : fs) {
            if (f.toString().indexOf("/.") != -1 || f.toString().indexOf("\\.") != -1) {
                continue;
            }
            if (f.isDirectory()) {
                // --if-- 若是目录，则递归统计该目录下的文件数量
                fileNum += getSonFileNum(f);
            }
            if (f.isFile()) {
                // --if-- 若是文件，添加到文件夹中
                fileNum++;
            }
        }
        return fileNum;
    }

    /**
     * 检查 备份源目录是否存在 和 准备好备份目标目录
     *
     * @param sourceId
     */
    private BackUpTask checkSourceAndTarget(String sourceId) {
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
            File file = new File(backupTarget.getTagetRootPath());
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
                if (!mkdir) {
                    throw new ServiceException("目标目录创建失败");
                }
            }
        }
        return new BackUpTask(source, backupSourceList);
    }
}
