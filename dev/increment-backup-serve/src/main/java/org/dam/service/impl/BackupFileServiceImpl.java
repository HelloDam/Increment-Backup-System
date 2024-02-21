package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.common.exception.ClientException;
import org.dam.common.page.PageResponse;
import org.dam.common.page.PageUtil;
import org.dam.common.utils.FileUtils;
import org.dam.common.utils.compress.GzipCompressUtil;
import org.dam.entity.BackupFile;
import org.dam.entity.request.BackupFileRequest;
import org.dam.mapper.BackupFileMapper;
import org.dam.service.BackupFileService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mac
 * @description 针对表【backup_file】的数据库操作Service实现
 * @createDate 2024-01-19 11:05:11
 */
@Service
public class BackupFileServiceImpl extends ServiceImpl<BackupFileMapper, BackupFile>
        implements BackupFileService {

    @Override
    public PageResponse<BackupFile> pageBackupFile(BackupFileRequest request) {
        QueryWrapper<BackupFile> queryWrapper = new QueryWrapper<>();
        if (request.getBackupSourceId() != null) {
            queryWrapper.like("backup_source_id", request.getBackupSourceId());
        }
        if (request.getBackupTargetId() != null) {
            queryWrapper.like("backup_target_id", request.getBackupTargetId());
        }
        if (!StringUtils.isEmpty(request.getSourceFilePath())) {
            queryWrapper.like("source_file_path", request.getSourceFilePath());
        }
        if (!StringUtils.isEmpty(request.getTargetFilePath())) {
            queryWrapper.like("target_file_path", request.getTargetFilePath());
        }
        // 根据时间降序排序
        queryWrapper.orderBy(true, false, "create_time");
        IPage<BackupFile> page = baseMapper.selectPage(new Page(request.getCurrent(), request.getSize()), queryWrapper);

        return PageUtil.convert(page);
    }

    @Override
    public void updateBackupNum(long fileId) {
        baseMapper.updateBackupNum(fileId);
    }

    @Override
    public List<BackupFile> buildTree(Long sourceId) {
        List<BackupFile> fileMessageList = baseMapper.selectList(new QueryWrapper<BackupFile>().eq("backup_source_id", sourceId));

        // 找出所有fatherId是0的
        List<BackupFile> rootList = fileMessageList.stream().filter(fileMessage -> fileMessage.getFatherId() == 0).collect(Collectors.toList());

        for (BackupFile root : rootList) {
            searchSon(root, fileMessageList);
        }

        return rootList;
    }

    /**
     * 递归删除一个文件夹下面的所有文件
     *
     * @param removeBackupFileIdList
     */
    @Override
    public void recursionRemoveBackupFile(List<Long> removeBackupFileIdList) {
        // 待删除ID集合中
        List<Long> totalRemoveBackupFileIdList = new ArrayList<>();
        // 递归方法：将文件的子文件id添加到待删除ID集合中
        searchBackupFileSons(removeBackupFileIdList, totalRemoveBackupFileIdList);
        // 批量删除
        if (totalRemoveBackupFileIdList.size() > 0){
            baseMapper.deleteBatchIds(totalRemoveBackupFileIdList);
        }
    }

    /**
     * 根据数据源ID和父ID查询出所有 fileMessage 数据
     *
     * @param sourceId
     * @param fatherId
     * @return
     */
    @Override
    public List<BackupFile> listChildrenBySourceIdAndFatherId(Long sourceId, Long fatherId) {
        return baseMapper.selectList(new QueryWrapper<BackupFile>().eq("backup_source_id", sourceId).eq("father_id", fatherId));
    }

    /**
     * 根据ID对指定的fileMessage进行解压操作，如果是目录，则递归解压其所有子文件
     * @param fileMessageId
     */
    @Override
    public void unCompress(Long fileMessageId) {
        BackupFile fileMessage = baseMapper.selectOne(new QueryWrapper<BackupFile>().eq("id", fileMessageId));
        if (fileMessage.getFileType() == 1 && fileMessage.getIsCompress() == 1) {
            // --if-- 是文件，直接解压
            String targetFilePath = fileMessage.getTargetFilePath().substring(0, fileMessage.getTargetFilePath().lastIndexOf(".")) + fileMessage.getFileSuffix();
            File file = new File(fileMessage.getTargetFilePath());
            if (!file.exists()){
                throw new ClientException("压缩包丢失，解压失败");
            }
            GzipCompressUtil.unCompressFile(file, targetFilePath);
        } else {
            // --if-- 是目录，解压子文件
            List<BackupFile> sonList = baseMapper.selectList(new QueryWrapper<BackupFile>().eq("father_id", fileMessageId));
            for (BackupFile message : sonList) {
                unCompress(message.getId());
            }
        }
    }

    @Override
    public byte[] downloadUnCompressFile(BackupFile fileMessage) {
        byte[] fileBytes;
        try {
            File file = new File(fileMessage.getTargetFilePath());
            if (!file.exists()){
                throw new ClientException("压缩包丢失，解压失败");
            }
            fileBytes = FileUtils.getFileBytes(file);
            byte[] compressedBytes = GzipCompressUtil.uncompress(fileBytes);
            return compressedBytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 递归方法：将文件的子文件id添加到待删除ID集合中
     *
     * @param removeBackupFileIdList
     * @param totalRemoveBackupFileIdList
     */
    private void searchBackupFileSons(List<Long> removeBackupFileIdList, List<Long> totalRemoveBackupFileIdList) {
        for (Long fileMessageId : removeBackupFileIdList) {
            totalRemoveBackupFileIdList.add(fileMessageId);
            List<Long> fileIdList = baseMapper.selectSonFileIdListByFatherId(fileMessageId);
            searchBackupFileSons(fileIdList, totalRemoveBackupFileIdList);
        }
    }

    /**
     * 找孩子
     *
     * @param father
     * @param fileMessageList
     */
    private void searchSon(BackupFile father, List<BackupFile> fileMessageList) {
        List<BackupFile> sonList = fileMessageList.stream().filter(item -> {
            if (item.getFatherId().equals(father.getId())) {
                return true;
            } else {
                return false;
            }
        }).map(son -> {
            //继续给儿子寻找孙子
            this.searchSon(son, fileMessageList);
            return son;
        }).collect(Collectors.toList());
        father.setChildren(sonList);
    }

}




