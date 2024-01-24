package org.dam.entity.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.dam.common.page.PageRequest;

import java.io.Serializable;

/**
 * @Author dam
 * @create 2024/1/20 23:10
 */
@Data
public class BackupTargetRequest extends PageRequest implements Serializable {

    /**
     * 数据源ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long backupSourceId;

    /**
     * 目标备份目录
     */
    private String targetRootPath;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
