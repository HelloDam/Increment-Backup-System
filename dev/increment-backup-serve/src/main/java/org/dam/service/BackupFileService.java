package org.dam.service;

import org.dam.common.page.PageResponse;
import org.dam.entity.BackupFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.dam.entity.request.BackupFileRequest;

import java.util.List;

/**
* @author mac
* @description 针对表【backup_file】的数据库操作Service
* @createDate 2024-01-19 11:05:11
*/
public interface BackupFileService extends IService<BackupFile> {

    PageResponse<BackupFile> pageBackupFile(BackupFileRequest pageRequest);

    void updateBackupNum(long fileId);

    List<BackupFile> buildTree(Long sourceId);

    void recursionRemoveBackupFile(List<Long> removeFileMessageIdList);

    List<BackupFile> listChildrenBySourceIdAndFatherId(Long sourceId, Long fatherId);

    void unCompress(Long fileMessageId);

    byte[] downloadUnCompressFile(BackupFile fileMessage);
}
