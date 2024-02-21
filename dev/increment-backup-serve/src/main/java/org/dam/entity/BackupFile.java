package org.dam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.dam.entity.base.BaseEntity;

/**
 * @TableName backup_file
 */
@TableName(value = "backup_file")
@Data
public class BackupFile extends BaseEntity implements Serializable {

    /**
     * 属于哪个数据源
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long backupSourceId;

    /**
     * 属于哪个备份目录
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long backupTargetId;

    /**
     * 源文件的路径
     */
    private String sourceFilePath;

    /**
     * 目标目录文件的路径
     */
    private String targetFilePath;

    /**
     * 备份数量
     */
    private Integer backupNum;

    /**
     * 文件类型 0：目录 1：文件
     */
    private Integer fileType;

    /**
     * 最后一次备份时间
     */
    private Date lastBackupTime;


    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件大小 byte
     */
    private Long fileLength;

    /**
     * 压缩之后的文件大小 byte
     */
    private Long fileLengthAfterCompress;

    /**
     * 父文件，如果没有设置为0
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fatherId;

    /**
     * 是否压缩 0：不压缩 1：压缩
     */
    private Integer isCompress;

    /**
     * 是否包含子文件 0：不包含 1：包含
     */
    private Integer isContainFile;

    /**
     * 子文件
     */
    @TableField(exist = false)
    private List<BackupFile> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}