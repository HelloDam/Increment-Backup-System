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
    public int counter;
    public long timestamp;
    /**
     * 备份的文件数量
     */
    public int backupFileNum;
    /**
     * 备份的字节数
     */
    public long backupByteNum;
}
