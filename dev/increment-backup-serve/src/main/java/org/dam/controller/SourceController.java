package org.dam.controller;

import org.dam.common.page.PageResponse;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.BackupSource;
import org.dam.entity.request.BackupSourceRequest;
import org.dam.service.BackupSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        sourceService.saveSource(backupSource);
        return Results.success();
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    public Result<PageResponse<BackupSource>> list(@RequestBody BackupSourceRequest sourceRequest) {
        PageResponse<BackupSource> backupSourcePageResponse = sourceService.pageBackupSource(sourceRequest);
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
    public Result update(@RequestBody BackupSource source) {
        sourceService.updateSourceById(source);
        return Results.success();
    }

    /**
     * 根据id获取数据源
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id){
        return Results.success(sourceService.getById(id));
    }
}
