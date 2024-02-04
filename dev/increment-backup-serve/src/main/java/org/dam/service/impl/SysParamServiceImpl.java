package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.common.constant.SystemConstant;
import org.dam.controller.SysParamController;
import org.dam.entity.SysParam;
import org.dam.entity.sysParam.IgnoreItem;
import org.dam.entity.sysParam.IgnoreParam;
import org.dam.service.SysParamService;
import org.dam.mapper.SysParamMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dam
 * @description 针对表【sys_param】的数据库操作Service实现
 * @createDate 2024-02-02 22:19:48
 */
@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParam>
        implements SysParamService {

    @Override
    public void updateIgnore(IgnoreParam ignoreParam) {
        StringBuilder strValueBuilder = new StringBuilder();
        if (ignoreParam.getIgnoreParamList().size() > 0) {
            for (int i = 0; i < ignoreParam.getIgnoreParamList().size() - 1; i++) {
                strValueBuilder.append(ignoreParam.getIgnoreParamList().get(i).getValue() + SystemConstant.SPLIT_STR);
            }
            strValueBuilder.append(ignoreParam.getIgnoreParamList().get(ignoreParam.getIgnoreParamList().size() - 1).getValue() + SystemConstant.SPLIT_STR);
        }
        baseMapper.updateIgnoreParam(ignoreParam.getParamName(), strValueBuilder.toString());
    }

    @Override
    public List<String> getIgnoreFileOrIgnoreDir(String paramName) {
        SysParam sysParam = baseMapper.selectOne(new QueryWrapper<SysParam>().eq("name", paramName));
        List<String> ignoreStrList = new ArrayList<>();
        if (sysParam != null && !StringUtils.isEmpty(sysParam.getValue())) {
            String[] valueList = sysParam.getValue().split(SystemConstant.SPLIT_STR);
            ignoreStrList = new ArrayList<>(Arrays.asList(valueList));
        }
        return ignoreStrList;
    }
}




