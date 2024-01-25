package org.dam.service;

import org.dam.common.page.PageResponse;
import org.dam.entity.BackupTask;
import com.baomidou.mybatisplus.extension.service.IService;
import org.dam.entity.request.BackupTaskRequest;

import java.util.List;

/**
* @author mac
* @description 针对表【backup_task】的数据库操作Service
* @createDate 2024-01-24 15:10:21
*/
public interface BackupTaskService extends IService<BackupTask> {

    List<BackupTask> listProcessingTask();

    void updateNotFinishedTask();

    PageResponse<BackupTask> pageBackupTask(BackupTaskRequest request);
}
