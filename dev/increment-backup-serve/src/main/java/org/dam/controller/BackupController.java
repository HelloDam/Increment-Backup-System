package org.dam.controller;

import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.User;
import org.dam.mapper.UserMapper;
import org.dam.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/backup")
public class BackupController {

    @Autowired
    private BackupService backupService;

    /**
     * 对 某源目录 执行备份
     */
    @GetMapping("/backupBySourceId")
    public Result backupBySourceId(String sourceId) {
        backupService.backupBySourceId(sourceId);
        return Results.success();
    }

}
