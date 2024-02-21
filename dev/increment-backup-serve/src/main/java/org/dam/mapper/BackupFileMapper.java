package org.dam.mapper;

import org.apache.ibatis.annotations.Param;
import org.dam.entity.BackupFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author mac
* @description 针对表【backup_file】的数据库操作Mapper
* @createDate 2024-01-19 11:05:11
* @Entity org.dam.entity.BackupFile
*/
public interface BackupFileMapper extends BaseMapper<BackupFile> {

    void updateBackupNum(@Param("fileId") long fileId);

    List<Long> selectSonFileIdListByFatherId(@Param("fatherId") Long fatherId);
}




