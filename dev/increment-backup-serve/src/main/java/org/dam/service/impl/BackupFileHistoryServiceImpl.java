package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.common.page.PageUtil;
import org.dam.entity.BackupFile;
import org.dam.entity.BackupFileHistory;
import org.dam.entity.request.BackupFileHistoryRequest;
import org.dam.service.BackupFileHistoryService;
import org.dam.mapper.BackupFileHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

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
    public PageResponse<BackupFileHistory> pageBackupFileHistory(BackupFileHistoryRequest request) {
        QueryWrapper<BackupFileHistory> queryWrapper = new QueryWrapper<>();
        if (request.getBackupFileId() != null) {
            queryWrapper.like("backup_file_id", request.getBackupFileId());
        }
        if (request.getBackupTargetRootId() != null) {
            queryWrapper.like("backup_target_root_id", request.getBackupTargetRootId());
        }
        if (request.getBackupTaskId() != null) {
            queryWrapper.like("backup_task_id", request.getBackupTaskId());
        }
        if (!StringUtils.isEmpty(request.getBackupTargetPath())) {
            queryWrapper.like("backup_target_path", request.getBackupTargetPath());
        }

        IPage<BackupFileHistory> page = baseMapper.selectPage(new Page(request.getCurrent(), request.getSize()), queryWrapper);

        return PageUtil.convert(page);
    }


}




