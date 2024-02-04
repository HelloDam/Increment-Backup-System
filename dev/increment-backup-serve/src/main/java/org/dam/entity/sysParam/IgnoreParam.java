package org.dam.entity.sysParam;

import lombok.Data;
import org.dam.controller.SysParamController;

import java.util.List;

/**
 * @Author dam
 * @create 2024/2/3 12:52
 */
@Data
public class IgnoreParam {
    private String paramName;
    private List<IgnoreItem> ignoreParamList;
}
