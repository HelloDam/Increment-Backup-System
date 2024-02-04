package org.dam.service;

import org.dam.controller.SysParamController;
import org.dam.entity.SysParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.dam.entity.sysParam.IgnoreParam;

import java.util.List;

/**
* @author dam
* @description 针对表【sys_param】的数据库操作Service
* @createDate 2024-02-02 22:19:48
*/
public interface SysParamService extends IService<SysParam> {

    void updateIgnore(IgnoreParam ignoreParam);

    List<String> getIgnoreFileOrIgnoreDir(String paramName);
}
