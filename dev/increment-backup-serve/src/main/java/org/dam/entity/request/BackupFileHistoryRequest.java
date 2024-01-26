package org.dam.entity.request;

import lombok.Data;
import org.dam.common.page.PageRequest;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author dam
 * @create 2024/1/25 19:43
 */
@Data
public class BackupFileHistoryRequest extends PageRequest implements Serializable {

    /**
     * 对应的备份文件id
     */
    private Long backupFileId;

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
    private Long backupTargetRootId;

    /**
     * 根据文件输入流生成的MD5码
     */
    private String md5;

    /**
     * 所属任务ID
     */
    private Long backupTaskId;

}
