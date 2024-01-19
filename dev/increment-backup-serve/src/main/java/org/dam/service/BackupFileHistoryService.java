package org.dam.service;

import org.dam.entity.BackupFileHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author mac
 * @description 针对表【backup_file_history】的数据库操作Service
 * @createDate 2024-01-19 11:05:11
 */
public interface BackupFileHistoryService extends IService<BackupFileHistory> {

    BackupFileHistory getLastBackupHistory(long fileId);
}
