package org.dam.service;

import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.entity.BackupFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.dam.entity.BackupTarget;
import org.dam.entity.request.BackupFileRequest;

/**
* @author mac
* @description 针对表【backup_file】的数据库操作Service
* @createDate 2024-01-19 11:05:11
*/
public interface BackupFileService extends IService<BackupFile> {

    PageResponse<BackupFile> pageBackupFile(BackupFileRequest pageRequest);
}
