package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.common.page.PageUtil;
import org.dam.entity.BackupFile;
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

    @Override
    public PageResponse<BackupFileHistory> pageBackupFile(PageRequest pageRequest) {
        LambdaQueryWrapper<BackupFileHistory> queryWrapper = Wrappers.lambdaQuery(BackupFileHistory.class)
                /* .orderByDesc(BackupSource::getCreateTime)*/;
        IPage<BackupFileHistory> page = baseMapper.selectPage(PageUtil.convert(pageRequest), queryWrapper);
        return PageUtil.convert(page);
    }
}




