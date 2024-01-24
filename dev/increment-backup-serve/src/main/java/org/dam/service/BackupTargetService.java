package org.dam.service;

import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.entity.BackupSource;
import org.dam.entity.BackupTarget;
import com.baomidou.mybatisplus.extension.service.IService;
import org.dam.entity.request.BackupTargetRequest;

/**
* @author mac
* @description 针对表【backup_target】的数据库操作Service
* @createDate 2024-01-19 11:05:11
*/
public interface BackupTargetService extends IService<BackupTarget> {

    PageResponse<BackupTarget> pageBackupTarget(BackupTargetRequest pageRequest);

    void updateTargetById(BackupTarget target);

    void saveTarget(BackupTarget target);
}
