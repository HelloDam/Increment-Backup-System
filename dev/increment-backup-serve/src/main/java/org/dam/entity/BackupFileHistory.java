package org.dam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName backup_file_history
 */
@TableName(value ="backup_file_history")
@Data
public class BackupFileHistory implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private Long backupFileId;

    /**
     * 
     */
    private Long modifyTime;

    /**
     * 
     */
    private Long fileSize;

    /**
     * 
     */
    private Date backupStartTime;

    /**
     * 
     */
    private Date backupEndTime;

    /**
     * 
     */
    private String backupTargetPath;

    /**
     * 
     */
    private Long backupTargetRootId;

    /**
     * 
     */
    private String md5;

    /**
     * 
     */
    private Long backupId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}