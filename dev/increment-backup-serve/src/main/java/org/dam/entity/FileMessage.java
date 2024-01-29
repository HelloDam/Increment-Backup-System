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
 * 
 * @TableName file_message
 */
@TableName(value ="file_message")
@Data
public class FileMessage extends BaseEntity implements Serializable {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 源文件路径
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long backupSourceId;

    /**
     * 源文件路径
     */
    private String sourceFilePath;

    /**
     * 备份目标文件路径
     */
    private String targetFilePath;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件大小 byte
     */
    private Integer fileLength;

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
     * 子文件
     */
    @TableField(exist = false)
    private List<FileMessage> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}