package org.dam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.entity.BackupTask;
import org.dam.service.BackupTaskService;
import org.dam.mapper.BackupTaskMapper;
import org.springframework.stereotype.Service;

/**
* @author mac
* @description 针对表【backup_task】的数据库操作Service实现
* @createDate 2024-01-24 15:10:21
*/
@Service
public class BackupTaskServiceImpl extends ServiceImpl<BackupTaskMapper, BackupTask>
    implements BackupTaskService{

}




