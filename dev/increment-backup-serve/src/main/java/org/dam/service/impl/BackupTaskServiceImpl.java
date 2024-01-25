package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.entity.BackupTask;
import org.dam.service.BackupTaskService;
import org.dam.mapper.BackupTaskMapper;
import org.springframework.stereotype.Service;

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
            task.setBackupProgress(String.format("%.1f", task.getFinishFileNum() * 100.0 / task.getTotalFileNum()));
        }
        return taskList;
    }

    @Override
    public void updateNotFinishedTask() {
        baseMapper.updateNotFinishedTask();
    }
}




