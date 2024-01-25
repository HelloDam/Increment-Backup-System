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
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 对应的备份文件id
     */
    private Long backupFileId;

    /**
     * 修改时间
     */
    private Long modifyTime;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 备份开始时间
     */
    private Date backupStartTime;

    /**
     * 备份结束时间
     */
    private Date backupEndTime;

    /**
     * 备份目标目录
     */
    private String backupTargetPath;

    /**
     * 备份目标目录ID
     */
    private Long backupTargetRootId;

    /**
     * 根据文件输入流生成的MD5码
     */
    private String md5;

    /**
     * 所属任务ID
     */
    private Long backupTaskId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}