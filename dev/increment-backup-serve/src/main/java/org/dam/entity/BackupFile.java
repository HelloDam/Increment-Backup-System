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
 * @TableName backup_file
 */
@TableName(value ="backup_file")
@Data
public class BackupFile implements Serializable {
    /**
     * 
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 
     */
    private Long backupSourceId;

    /**
     * 
     */
    private String filePath;

    /**
     * 
     */
    private Integer backupNum;

    /**
     * 
     */
    private Date lastBackupTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}