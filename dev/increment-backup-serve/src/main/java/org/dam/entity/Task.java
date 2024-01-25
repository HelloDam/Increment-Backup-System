package org.dam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author dam
 * @create 2024/1/19 14:26
 */
@Data
@AllArgsConstructor
public class Task {
    private BackupSource source;
    private BackupTarget target;

}
