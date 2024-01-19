package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.common.page.PageUtil;
import org.dam.entity.BackupFile;
import org.dam.entity.BackupSource;
import org.dam.entity.BackupTarget;
import org.dam.service.BackupFileService;
import org.dam.mapper.BackupFileMapper;
import org.springframework.stereotype.Service;

/**
* @author mac
* @description 针对表【backup_file】的数据库操作Service实现
* @createDate 2024-01-19 11:05:11
*/
@Service
public class BackupFileServiceImpl extends ServiceImpl<BackupFileMapper, BackupFile>
    implements BackupFileService{

    @Override
    public PageResponse<BackupFile> pageBackupFile(PageRequest pageRequest) {
        LambdaQueryWrapper<BackupFile> queryWrapper = Wrappers.lambdaQuery(BackupFile.class)
                /* .orderByDesc(BackupSource::getCreateTime)*/;
        IPage<BackupFile> page = baseMapper.selectPage(PageUtil.convert(pageRequest), queryWrapper);
        return PageUtil.convert(page);
    }
}




