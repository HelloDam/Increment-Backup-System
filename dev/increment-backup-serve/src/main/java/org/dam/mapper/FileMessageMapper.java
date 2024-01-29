package org.dam.mapper;

import org.apache.ibatis.annotations.Param;
import org.dam.entity.FileMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author dam
* @description 针对表【file_message】的数据库操作Mapper
* @createDate 2024-01-28 21:38:07
* @Entity org.dam.entity.FileMessage
*/
public interface FileMessageMapper extends BaseMapper<FileMessage> {

    List<Long> selectSonFileIdListByFatherId(@Param("fatherId") Long fatherId);
}




