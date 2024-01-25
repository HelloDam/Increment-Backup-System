package org.dam.controller;

import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.BackupTarget;
import org.dam.entity.BackupTarget;
import org.dam.entity.BackupTarget;
import org.dam.entity.request.BackupTargetRequest;
import org.dam.service.BackupTargetService;
import org.dam.service.BackupTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/target")
public class TargetController {

    @Autowired
    private BackupTargetService targetService;

    /**
     * 增添数据
     */
    @GetMapping("/insertTarget")
    public Result insertTarget(long backupSourceId, String targetRootPath) {
        BackupTarget backupTarget = new BackupTarget();
        backupTarget.setBackupSourceId(backupSourceId);
        backupTarget.setTargetRootPath(targetRootPath);

        targetService.save(backupTarget);
        return Results.success();
    }

    /**
     * 增添数据
     */
    @PostMapping("/save")
    public Result save(@RequestBody BackupTarget backupSource) {
        targetService.saveTarget(backupSource);
        return Results.success();
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    public Result<PageResponse<BackupTarget>> list(@RequestBody BackupTargetRequest request) {
        PageResponse<BackupTarget> backupTargetPageResponse = targetService.pageBackupTarget(request);
        return Results.success(backupTargetPageResponse);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/removeById")
    public Result removeById(Integer id) {
        targetService.removeById(id);
        return Results.success();
    }

    /**
     * 删除数据
     */
    @PostMapping("/removeByIds")
    public Result removeByIds(@RequestBody List<Long> idList) {
        targetService.removeByIds(idList);
        return Results.success();
    }

    /**
     * 修改数据
     */
    @PostMapping("/update")
    public Result update(@RequestBody BackupTarget target) {
        targetService.updateTargetById(target);
        return Results.success();
    }

    /**
     * 根据id获取数据源
     *
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id) {
        return Results.success(targetService.getById(id));
    }
}

