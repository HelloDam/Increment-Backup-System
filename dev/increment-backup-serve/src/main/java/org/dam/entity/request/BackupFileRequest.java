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
public class BackupFileRequest extends PageRequest implements Serializable {

    /**
     * 属于哪个数据源
     */
    private Long backupSourceId;

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
