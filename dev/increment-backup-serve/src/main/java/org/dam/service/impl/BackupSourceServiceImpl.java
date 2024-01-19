package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.common.page.PageUtil;
import org.dam.entity.BackupSource;
import org.dam.service.BackupSourceService;
import org.dam.mapper.BackupSourceMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mac
 * @description 针对表【backup_source】的数据库操作Service实现
 * @createDate 2024-01-19 11:05:11
 */
@Service
public class BackupSourceServiceImpl extends ServiceImpl<BackupSourceMapper, BackupSource>
        implements BackupSourceService {

    @Override
    public PageResponse<BackupSource> pageBackupSource(PageRequest pageRequest) {
        LambdaQueryWrapper<BackupSource> queryWrapper = Wrappers.lambdaQuery(BackupSource.class)
               /* .orderByDesc(BackupSource::getCreateTime)*/;
        IPage<BackupSource> page = baseMapper.selectPage(PageUtil.convert(pageRequest), queryWrapper);
        return PageUtil.convert(page);
    }
}




