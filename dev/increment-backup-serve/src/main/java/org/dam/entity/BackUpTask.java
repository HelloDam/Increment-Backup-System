package org.dam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Author dam
 * @create 2024/1/19 14:26
 */
@Data
@AllArgsConstructor
public class BackUpTask {
    private BackupSource source;
    private List<BackupTarget> targetList;
}
