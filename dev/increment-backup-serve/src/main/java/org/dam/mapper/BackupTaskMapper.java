package org.dam.mapper;

import org.dam.entity.BackupTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author mac
* @description 针对表【backup_task】的数据库操作Mapper
* @createDate 2024-01-24 15:10:21
* @Entity org.dam.entity.BackupTask
*/
public interface BackupTaskMapper extends BaseMapper<BackupTask> {

    void updateNotFinishedTask();

}




