package org.dam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.dam.entity.base.BaseEntity;

/**
 * 
 * @TableName backup_target
 */
@TableName(value ="backup_target")
@Data
public class BackupTarget extends BaseEntity implements Serializable {

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