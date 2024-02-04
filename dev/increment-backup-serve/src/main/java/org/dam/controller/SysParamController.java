package org.dam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.dam.common.constant.SystemConstant;
import org.dam.common.constant.SystemParamEnum;
import org.dam.common.page.PageResponse;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.BackupFile;
import org.dam.entity.SysParam;
import org.dam.entity.request.BackupFileRequest;
import org.dam.entity.sysParam.IgnoreItem;
import org.dam.entity.sysParam.IgnoreParam;
import org.dam.service.BackupFileService;
import org.dam.service.SysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/sysPram")
public class SysParamController {

    @Autowired
    private SysParamService sysParamService;

    /**
     * 根据参数名称获取参数值
     *
     * @param paramName
     * @return
     */
    @GetMapping("/getByParamName/{paramName}")
    public Result getByParamName(@PathVariable String paramName) {
        if (SystemParamEnum.IGNORE_FILE_NAME.getParamName().equals(paramName)
                || SystemParamEnum.IGNORE_DIRECTORY_NAME.getParamName().equals(paramName)) {
            SysParam sysParam = sysParamService.getOne(new QueryWrapper<SysParam>().eq("name", paramName));
            if (sysParam == null) {
                if (SystemParamEnum.IGNORE_FILE_NAME.getParamName().equals(paramName)) {
                    sysParam = new SysParam();
                    sysParam.setName(SystemParamEnum.IGNORE_FILE_NAME.getParamName());
                    sysParam.setValue(null);
                    sysParam.setDescription(SystemParamEnum.IGNORE_FILE_NAME.getDetail());
                } else if (SystemParamEnum.IGNORE_DIRECTORY_NAME.getParamName().equals(paramName)) {
                    sysParam = new SysParam();
                    sysParam.setName(SystemParamEnum.IGNORE_DIRECTORY_NAME.getParamName());
                    sysParam.setValue(null);
                    sysParam.setDescription(SystemParamEnum.IGNORE_DIRECTORY_NAME.getDetail());
                }
                sysParamService.save(sysParam);
            }
            List<IgnoreItem> ignoreItemList = new ArrayList<>();
            if (sysParam != null && !StringUtils.isEmpty(sysParam.getValue())) {
                String[] valueList = sysParam.getValue().split(SystemConstant.SPLIT_STR);
                for (int i = 0; i < valueList.length; i++) {
                    ignoreItemList.add(new IgnoreItem(i, valueList[i]));
                }
            }
            return Results.success(ignoreItemList);
        }
        return Results.success();
    }

    /**
     * 修改数据
     */
    @PostMapping("/updateIgnore")
    public Result updateIgnore(@RequestBody IgnoreParam ignoreParam) {
        sysParamService.updateIgnore(ignoreParam);
        return Results.success();
    }




}

