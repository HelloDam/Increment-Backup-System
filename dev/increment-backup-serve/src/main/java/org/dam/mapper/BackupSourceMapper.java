package org.dam.mapper;

import org.apache.ibatis.annotations.Param;
import org.dam.entity.BackupSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author mac
* @description 针对表【backup_source】的数据库操作Mapper
* @createDate 2024-01-19 11:05:11
* @Entity org.dam.entity.BackupSource
*/
public interface BackupSourceMapper extends BaseMapper<BackupSource> {

    void updateBackupNum(@Param("sourceId") Long sourceId);
}




