package org.dam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @TableName backup_file_history
 */
@TableName(value ="backup_file_history")
@Data
@ToString
public class BackupFileHistory implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 对应的备份文件id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long backupFileId;

    /**
     * 属于哪个数据源
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long backupSourceId;

    /**
     * 备份源文件路径
     */
    private String backupSourceFilePath;

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
    private String backupTargetFilePath;

    /**
     * 备份目标目录ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long backupTargetRootId;

    /**
     * 根据文件输入流生成的MD5码
     */
    private String md5;

    /**
     * 所属任务ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long backupTaskId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}