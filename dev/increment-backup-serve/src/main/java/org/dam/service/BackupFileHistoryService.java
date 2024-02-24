package org.dam.service;

import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.entity.BackupFileHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import org.dam.entity.request.BackupFileHistoryRequest;

import java.util.List;

/**
 * @author mac
 * @description 针对表【backup_file_history】的数据库操作Service
 * @createDate 2024-01-19 11:05:11
 */
public interface BackupFileHistoryService extends IService<BackupFileHistory> {

    BackupFileHistory getLastBackupHistory(long fileId);

    PageResponse<BackupFileHistory> pageBackupFileHistory(BackupFileHistoryRequest request);

    void removeByFileIds(List<Long> removeBackupFileIdList);

    List<BackupFileHistory> listFileHistoryListByFileIds(List<Long> removeBackupFileIdList);

    List<BackupFileHistory> listLastBackupHistoryByBackupFileIdList(List<Long> exitBackupFileIdList);

    void updateBatch(List<BackupFileHistory> buffer);
}
