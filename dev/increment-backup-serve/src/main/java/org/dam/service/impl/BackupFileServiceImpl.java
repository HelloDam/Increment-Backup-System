package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.common.page.PageUtil;
import org.dam.entity.BackupFile;
import org.dam.entity.BackupSource;
import org.dam.entity.BackupTarget;
import org.dam.entity.BackupFile;
import org.dam.entity.request.BackupFileRequest;
import org.dam.service.BackupFileService;
import org.dam.mapper.BackupFileMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author mac
 * @description 针对表【backup_file】的数据库操作Service实现
 * @createDate 2024-01-19 11:05:11
 */
@Service
public class BackupFileServiceImpl extends ServiceImpl<BackupFileMapper, BackupFile>
        implements BackupFileService {

    @Override
    public PageResponse<BackupFile> pageBackupFile(BackupFileRequest request) {
        QueryWrapper<BackupFile> queryWrapper = new QueryWrapper<>();
        if (request.getBackupSourceId() != null) {
            queryWrapper.like("backup_source_id", request.getBackupSourceId());
        }
        if (request.getBackupTargetId() != null) {
            queryWrapper.like("backup_target_id", request.getBackupTargetId());
        }
        if (!StringUtils.isEmpty(request.getSourceFilePath())) {
            queryWrapper.like("source_file_path", request.getSourceFilePath());
        }
        if (!StringUtils.isEmpty(request.getTargetFilePath())) {
            queryWrapper.like("target_file_path", request.getTargetFilePath());
        }

        IPage<BackupFile> page = baseMapper.selectPage(new Page(request.getCurrent(), request.getSize()), queryWrapper);

        return PageUtil.convert(page);
    }

    @Override
    public void updateBackupNum(long fileId) {
        baseMapper.updateBackupNum(fileId);
    }
}




