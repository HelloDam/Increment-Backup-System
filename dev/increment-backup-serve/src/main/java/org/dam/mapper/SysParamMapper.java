package org.dam.mapper;

import org.apache.ibatis.annotations.Param;
import org.dam.entity.SysParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author dam
* @description 针对表【sys_param】的数据库操作Mapper
* @createDate 2024-02-02 22:19:48
* @Entity org.dam.entity.SysParam
*/
public interface SysParamMapper extends BaseMapper<SysParam> {

    void updateIgnoreParam(@Param("paramName") String paramName, @Param("valueStr") String valueStr);
}




