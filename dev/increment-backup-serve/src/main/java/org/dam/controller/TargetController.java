package org.dam.controller;

import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.BackupTarget;
import org.dam.entity.BackupTarget;
import org.dam.service.BackupTargetService;
import org.dam.service.BackupTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        backupTarget.setTagetRootPath(targetRootPath);

        targetService.save(backupTarget);
        return Results.success();
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    public Result<PageResponse<BackupTarget>> list(PageRequest pageRequest) {
        return Results.success(targetService.pageBackupTarget(pageRequest));
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
     * 修改数据
     */
    @PostMapping("/update")
    public Result update(BackupTarget target) {
        targetService.updateById(target);
        return Results.success();
    }
}
