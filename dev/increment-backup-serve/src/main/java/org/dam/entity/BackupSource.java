package org.dam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;
import org.dam.common.page.PageRequest;
import org.dam.entity.base.BaseEntity;

/**
 * @TableName backup_source
 */
@TableName(value = "backup_source")
@Data
@Accessors(chain = true)
public class BackupSource extends BaseEntity implements Serializable {
    /**
     * 根目录
     */
    private String rootPath;

    /**
     * 备份名称，可以总结一下是备份什么的
     */
    private String backupName;

    /**
     * 备份类型 0：数据源的数据全部备份到多个目标目录中 1：数据源的数据分散备份到多个目标目录中
     */
    private Integer backupType;

    /**
     * 备份次数
     */
    private Integer backupNum = 0;

    /**
     * 是否压缩 0：不压缩 1：压缩
     */
    private Integer isCompress;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}