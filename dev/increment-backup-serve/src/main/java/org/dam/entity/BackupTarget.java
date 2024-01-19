package org.dam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
     * 
     */
    private Long backupSourceId;

    /**
     * 
     */
    private String tagetRootPath;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}