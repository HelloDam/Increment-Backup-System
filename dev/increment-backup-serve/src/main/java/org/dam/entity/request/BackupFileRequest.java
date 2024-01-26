package org.dam.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.dam.common.page.PageRequest;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author dam
 * @create 2024/1/25 19:43
 */
@Data
public class BackupFileRequest extends PageRequest implements Serializable {

    /**
     * 属于哪个数据源
     */
    private Long backupSourceId;

    /**
     * 属于哪个备份目录
     */
    private Long backupTargetId;

    /**
     * 文件的路径
     */
    private String filePath;

    /**
     * 备份数量
     */
    private Integer backupNum;

    /**
     * 最后一次备份时间
     */
    private Date lastBackupTime;

}
