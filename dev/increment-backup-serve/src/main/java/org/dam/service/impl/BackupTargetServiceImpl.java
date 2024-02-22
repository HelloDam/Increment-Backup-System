package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.common.exception.ClientException;
import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.common.page.PageUtil;
import org.dam.entity.BackupTarget;
import org.dam.entity.request.BackupTargetRequest;
import org.dam.service.BackupTargetService;
import org.dam.mapper.BackupTargetMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @author mac
 * @description 针对表【backup_target】的数据库操作Service实现
 * @createDate 2024-01-19 11:05:11
 */
@Service
public class BackupTargetServiceImpl extends ServiceImpl<BackupTargetMapper, BackupTarget>
        implements BackupTargetService {

    @Override
    public PageResponse<BackupTarget> pageBackupTarget(BackupTargetRequest pageRequest) {
        QueryWrapper<BackupTarget> queryWrapper = new QueryWrapper<>();
        if (pageRequest.getBackupSourceId() != null) {
            queryWrapper.eq("backup_source_id", pageRequest.getBackupSourceId());
        }

        if (!StringUtils.isEmpty(pageRequest.getTargetRootPath())) {
            queryWrapper.like("target_root_path", pageRequest.getTargetRootPath());
        }
        queryWrapper.orderBy(true, false, "create_time");
        IPage<BackupTarget> page = baseMapper.selectPage(PageUtil.convert(pageRequest), queryWrapper);
        return PageUtil.convert(page);
    }

    @Override
    public void updateTargetById(BackupTarget target) {
        checkTarget(target);
        baseMapper.updateById(target);
    }

    @Override
    public void saveTarget(BackupTarget target) {
        checkTarget(target);
        baseMapper.insert(target);
    }

    /**
     * 检查数据备份目标目录是否正确
     *
     * @param target
     * @return
     */
    //todo 待完善对目录的判断
    private void checkTarget(BackupTarget target) {
        if (target == null) {
            throw new ClientException("数据备份目标目录路径不能为空");
        }
     /*   File file = new File(target.getTagetRootPath());
        if (!file.exists()) {
            throw new ClientException("数据备份目标目录路径没有对应的目录，请检查路径是否正确");
        }
        if (!file.isDirectory()) {
            throw new ClientException("数据备份目标目录路径不是一个目录，而是一个文件，请检查路径是否正确");
        }*/
    }
}




