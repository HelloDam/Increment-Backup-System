package org.dam.mapper;

import org.apache.ibatis.annotations.Param;
import org.dam.entity.BackupFileHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author mac
* @description 针对表【backup_file_history】的数据库操作Mapper
* @createDate 2024-01-19 11:05:11
* @Entity org.dam.entity.BackupFileHistory
*/
public interface BackupFileHistoryMapper extends BaseMapper<BackupFileHistory> {

    BackupFileHistory getLastBackupHistory(@Param("fileId") long fileId);

    void removeByFileIds(@Param("removeBackupFileIdList") List<Long> removeBackupFileIdList);

    List<BackupFileHistory> listLastBackupHistoryByBackupFileIdList(@Param("exitBackupFileIdList") List<Long> exitBackupFileIdList);

    void updateBatch(@Param("buffer") List<BackupFileHistory> buffer);
}




