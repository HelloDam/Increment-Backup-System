package org.dam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.entity.BackupFileHistory;
import org.dam.service.BackupFileHistoryService;
import org.dam.mapper.BackupFileHistoryMapper;
import org.springframework.stereotype.Service;

/**
 * @author mac
 * @description 针对表【backup_file_history】的数据库操作Service实现
 * @createDate 2024-01-19 11:05:11
 */
@Service
public class BackupFileHistoryServiceImpl extends ServiceImpl<BackupFileHistoryMapper, BackupFileHistory>
        implements BackupFileHistoryService {

    @Override
    public BackupFileHistory getLastBackupHistory(long fileId) {
        return baseMapper.getLastBackupHistory(fileId);
    }
}




