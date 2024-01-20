package org.dam.controller;

import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.BackupSource;
import org.dam.entity.User;
import org.dam.mapper.UserMapper;
import org.dam.service.BackupSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/source")
public class SourceController {

    @Autowired
    private BackupSourceService sourceService;

    /**
     * 增添数据
     */
    @GetMapping("/insertSource")
    public Result insertSource(String rootPath, String backupName) {
        BackupSource backupSource = new BackupSource();
        backupSource.setRootPath(rootPath);
        backupSource.setBackupName(backupName);
        sourceService.save(backupSource);
        return Results.success();
    }

    /**
     * 增添数据
     */
    @PostMapping("/save")
    public Result save(@RequestBody BackupSource backupSource) {
        sourceService.save(backupSource);
        return Results.success();
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    public Result<PageResponse<BackupSource>> list(PageRequest pageRequest) {
        PageResponse<BackupSource> backupSourcePageResponse = sourceService.pageBackupSource(pageRequest);
        return Results.success(backupSourcePageResponse);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/removeById")
    public Result removeById(Integer id) {
        sourceService.removeById(id);
        return Results.success();
    }

    /**
     * 删除数据
     */
    @PostMapping("/removeByIds")
    public Result removeByIds(@RequestBody List<Long> idList) {
        sourceService.removeByIds(idList);
        return Results.success();
    }

    /**
     * 修改数据
     */
    @PostMapping("/update")
    public Result update(BackupSource source) {
        sourceService.updateById(source);
        return Results.success();
    }
}
