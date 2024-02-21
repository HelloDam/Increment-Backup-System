package org.dam.controller;

import lombok.extern.slf4j.Slf4j;
import org.dam.common.exception.ClientException;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.Task;
import org.dam.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/backup")
@Slf4j
public class BackupController {

    @Autowired
    private BackupService backupService;
    @Autowired
    private ThreadPoolExecutor executor;

    /**
     * 存储正在备份的数据源ID
     */
    public HashSet<Long> sourceIDSet = new HashSet<>();

    /**
     * 对指定的数据源进行备份
     */
    @GetMapping("/backupBySourceId/{sourceId}")
    public Result backupBySourceId(@PathVariable Long sourceId) {
        if (sourceIDSet.contains(sourceId)) {
            throw new ClientException("当前备份源正在备份中，请稍后再试");
        } else {
            sourceIDSet.add(sourceId);
        }

        // 检查 备份源目录是否存在 和 准备好备份目标目录
        List<Task> taskList = backupService.checkSourceAndTarget(sourceId);
        CompletableFuture.runAsync(() -> {
            backupService.backupBySourceId(sourceId, taskList);
        }, executor).exceptionally(throwable -> {
            log.error(throwable.getMessage());
            if (sourceIDSet.contains(sourceId)) {
                sourceIDSet.remove(sourceId);
            }
            return null;
        });

        return Results.success();
    }

    /**
     * 对指定的数据源删除无效数据
     */
    @GetMapping("/clearBySourceId/{sourceId}")
    public Result clearBySourceId(@PathVariable Long sourceId) {
        backupService.clearBySourceId(sourceId);
        return Results.success();
    }

}
