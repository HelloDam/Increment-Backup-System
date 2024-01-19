package org.dam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.entity.BackupTarget;
import org.dam.service.BackupTargetService;
import org.dam.mapper.BackupTargetMapper;
import org.springframework.stereotype.Service;

/**
* @author mac
* @description 针对表【backup_target】的数据库操作Service实现
* @createDate 2024-01-19 11:05:11
*/
@Service
public class BackupTargetServiceImpl extends ServiceImpl<BackupTargetMapper, BackupTarget>
    implements BackupTargetService{

}




