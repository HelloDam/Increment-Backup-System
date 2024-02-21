package org.dam.service;

import org.dam.entity.Task;

import java.io.IOException;
import java.util.List;

/**
 * @Author dam
 * @create 2024/1/19 13:53
 */
public interface BackupService {
    void backupBySourceId(Long sourceId, List<Task> taskList) throws IOException;

    void clearBySourceId(Long sourceId);

    List<Task> checkSourceAndTarget(Long sourceId);
}
