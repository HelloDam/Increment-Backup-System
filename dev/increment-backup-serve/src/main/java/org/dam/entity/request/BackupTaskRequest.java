package org.dam.entity.request;

import lombok.Data;
import org.dam.common.page.PageRequest;

import java.io.Serializable;

/**
 * @Author dam
 * @create 2024/1/25 19:43
 */
@Data
public class BackupTaskRequest extends PageRequest implements Serializable {

    /**
     * 备份数据源根目录
     */
    private String backupSourceRoot;

    /**
     * 备份目标根目录
     */
    private String backupTargetRoot;

    /**
     * 该次备份的总文件数量
     */
    private Integer totalFileNum;

    /**
     * 已经完成备份的总文件数量（并非完全最准确，一秒钟统计一次，如果服务被切断，这个数据就不再可信）
     */
    private Integer finishFileNum;

    /**
     * 该次备份的总字节数
     */
    private Long totalByteNum;

    /**
     * 已完成备份的总字节数
     */
    private Long finishByteNum;

    /**
     * 状态 0：刚创建 1：正在执行备份 2：备份完成 3：备份失败
     */
    private Integer backupStatus;

}
