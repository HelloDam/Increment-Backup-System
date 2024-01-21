package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.common.exception.ClientException;
import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.common.page.PageUtil;
import org.dam.entity.BackupSource;
import org.dam.entity.request.BackupSourceRequest;
import org.dam.service.BackupSourceService;
import org.dam.mapper.BackupSourceMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
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
    public PageResponse<BackupSource> pageBackupSource(BackupSourceRequest sourceRequest) {
        QueryWrapper<BackupSource> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(sourceRequest.getRootPath())) {
            queryWrapper.like("root_path", sourceRequest.getRootPath());
        }
        if (!StringUtils.isEmpty(sourceRequest.getBackupName())) {
            queryWrapper.like("backup_name", sourceRequest.getBackupName());
        }
        IPage<BackupSource> page = baseMapper.selectPage(new Page(sourceRequest.getCurrent(),sourceRequest.getSize()), queryWrapper);

        return PageUtil.convert(page);
    }

    @Override
    public void saveSource(BackupSource backupSource) {
        checkSource(backupSource);
        baseMapper.insert(backupSource);
    }

    @Override
    public void updateSourceById(BackupSource backupSource) {
        checkSource(backupSource);
        baseMapper.updateById(backupSource);
    }

    /**
     * 检查数据源是否正确
     *
     * @param backupSource
     * @return
     */
    private void checkSource(BackupSource backupSource) {
        if (backupSource == null) {
            throw new ClientException("数据源根路径不能为空");
        }
        File file = new File(backupSource.getRootPath());
        if (!file.exists()) {
            throw new ClientException("数据源根路径没有对应的目录，请检查路径是否正确");
        }
        if (!file.isDirectory()) {
            throw new ClientException("数据源根路径不是一个目录，而是一个文件，请检查路径是否正确");
        }
    }
}




