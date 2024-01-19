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
     *
     */
    private String rootPath;

    /**
     * 备份名称，可以总结一下是备份什么的
     */
    private String backupName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}