package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.common.page.PageResponse;
import org.dam.common.page.PageUtil;
import org.dam.entity.BackupFileHistory;
import org.dam.entity.request.BackupFileHistoryRequest;
import org.dam.service.BackupFileHistoryService;
import org.dam.mapper.BackupFileHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
        if (!StringUtils.isEmpty(request.getBackupTargetFilePath())) {
            queryWrapper.like("backup_target_file_path", request.getBackupTargetFilePath());
        }
        if (!StringUtils.isEmpty(request.getBackupSourceFilePath())) {
            queryWrapper.like("backup_source_file_path", request.getBackupSourceFilePath());
        }
        // 根据时间降序排序
        queryWrapper.orderBy(true, false, "backup_end_time");
        IPage<BackupFileHistory> page = baseMapper.selectPage(new Page(request.getCurrent(), request.getSize()), queryWrapper);

        return PageUtil.convert(page);
    }

    @Override
    public void removeByFileIds(List<Long> removeBackupFileIdList) {
        baseMapper.removeByFileIds(removeBackupFileIdList);
    }

    @Override
    public List<BackupFileHistory> listFileHistoryListByFileIds(List<Long> removeBackupFileIdList) {
        return baseMapper.selectList(new QueryWrapper<BackupFileHistory>().in("backup_file_id", removeBackupFileIdList));
    }

    @Override
    public List<BackupFileHistory> listLastBackupHistoryByBackupFileIdList(List<Long> exitBackupFileIdList) {
        if (exitBackupFileIdList.size() > 0) {
            return baseMapper.listLastBackupHistoryByBackupFileIdList(exitBackupFileIdList);
        } else {
            return new ArrayList<>();
        }
    }

}




