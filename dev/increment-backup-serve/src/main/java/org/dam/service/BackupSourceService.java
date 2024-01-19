package org.dam.service;

import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.entity.BackupSource;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author mac
* @description 针对表【backup_source】的数据库操作Service
* @createDate 2024-01-19 11:05:11
*/
public interface BackupSourceService extends IService<BackupSource> {

    PageResponse<BackupSource> pageBackupSource(PageRequest pageRequest);

}
