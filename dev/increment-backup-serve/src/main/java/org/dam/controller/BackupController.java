package org.dam.controller;

import org.dam.common.exception.ClientException;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/backup")
public class BackupController {

    @Autowired
    private BackupService backupService;
    @Autowired
    private ThreadPoolExecutor executor;

    /**
     * 存储正在备份的数据源ID
     */
    public HashSet<String> sourceIDSet = new HashSet<>();

    /**
     * 对指定的数据源进行备份
     */
    @GetMapping("/backupBySourceId/{sourceId}")
    public Result backupBySourceId(@PathVariable String sourceId) {
        if (sourceIDSet.contains(sourceId)) {
            throw new ClientException("当前备份源正在备份中，请稍后再试");
        } else {
            sourceIDSet.add(sourceId);
        }

        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            backupService.backupBySourceId(sourceId);
        }, executor);

        return Results.success();
    }

    public Result clear() {
        return Results.success();
    }

}
