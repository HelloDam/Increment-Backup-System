package org.dam.service;

/**
 * @Author dam
 * @create 2024/1/19 13:53
 */
public interface BackupService {
    void backupBySourceId(String sourceId);

    void clearBySourceId(String sourceId);
}
