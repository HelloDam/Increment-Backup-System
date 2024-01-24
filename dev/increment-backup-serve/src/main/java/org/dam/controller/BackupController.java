package org.dam.controller;

import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.User;
import org.dam.mapper.UserMapper;
import org.dam.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 对指定的数据源进行备份
     */
    @GetMapping("/backupBySourceId/{sourceId}")
    public Result backupBySourceId(@PathVariable String sourceId) {

        CompletableFuture.runAsync(() -> {
            backupService.backupBySourceId(sourceId);
        }, executor);

        return Results.success();
    }

    public Result clear() {
        return Results.success();
    }

}
