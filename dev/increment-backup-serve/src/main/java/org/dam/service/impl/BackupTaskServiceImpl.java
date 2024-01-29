package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.common.page.PageResponse;
import org.dam.common.page.PageUtil;
import org.dam.entity.BackupSource;
import org.dam.entity.BackupTask;
import org.dam.entity.request.BackupTaskRequest;
import org.dam.service.BackupTaskService;
import org.dam.mapper.BackupTaskMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author mac
 * @description 针对表【backup_task】的数据库操作Service实现
 * @createDate 2024-01-24 15:10:21
 */
@Service
public class BackupTaskServiceImpl extends ServiceImpl<BackupTaskMapper, BackupTask>
        implements BackupTaskService {

    @Override
    public List<BackupTask> listProcessingTask() {
        // 查询出还没有完成的任务，或者是当前正在执行的任务
        QueryWrapper<BackupTask> backupTaskQueryWrapper = new QueryWrapper<>();
        backupTaskQueryWrapper.eq("backup_status", 1);
        backupTaskQueryWrapper.orderByDesc("create_time");
        List<BackupTask> taskList = baseMapper.selectList(backupTaskQueryWrapper);
        for (BackupTask task : taskList) {
            setProgress(task);
        }
        return taskList;
    }

    @Override
    public void updateNotFinishedTask() {
        baseMapper.updateNotFinishedTask();
    }

    @Override
    public PageResponse<BackupTask> pageBackupTask(BackupTaskRequest request) {
        QueryWrapper<BackupTask> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(request.getBackupSourceRoot())) {
            queryWrapper.like("backup_source_root", request.getBackupSourceRoot());
        }
        if (!StringUtils.isEmpty(request.getBackupTargetRoot())) {
            queryWrapper.like("backup_target_root", request.getBackupTargetRoot());
        }
        if (request.getBackupStatus() != null) {
            queryWrapper.eq("backup_status", request.getBackupStatus());
        }
        // 根据时间降序排序
        queryWrapper.orderBy(true, false, "create_time");
        Page pageParam = new Page(request.getCurrent(), request.getSize());
        IPage<BackupTask> page = baseMapper.selectPage(pageParam, queryWrapper);
        for (BackupTask task : page.getRecords()) {
            setProgress(task);
            task.setBackupTime(task.getEndTime().getTime() - task.getCreateTime().getTime());
        }

        return PageUtil.convert(page);
    }

    /**
     * 设置进度
     *
     * @param task
     */
    public static void setProgress(BackupTask task) {
        task.setBackupProgress(String.format("%.1f", task.getFinishFileNum() * 100.0 / task.getTotalFileNum()));
    }
}




