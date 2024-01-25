package org.dam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dam.entity.base.BaseEntity;

/**
 * 
 * @TableName backup_task
 */
@TableName(value ="backup_task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackupTask extends BaseEntity implements Serializable {

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
//    @TableField(exist = false)
    private Integer backupStatus;

    /**
     * 备份进度百分比
     */
    @TableField(exist = false)
    private String backupProgress;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}