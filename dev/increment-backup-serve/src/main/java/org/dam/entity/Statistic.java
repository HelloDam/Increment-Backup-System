package org.dam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author dam
 * @create 2024/1/18 14:36
 */
@Data
@AllArgsConstructor
public class Statistic {
    /**
     * 已完成备份的文件数量
     */
    public int finishBackupFileNum;
    /**
     * 已完成备份的字节数量
     */
    public long finishBackupByteNum;
    /**
     * 备份的文件数量
     */
    public int totalBackupFileNum;
    /**
     * 备份的字节数
     */
    public long totalBackupByteNum;
    /**
     * 记录时间戳
     */
    public long timestamp;

}
