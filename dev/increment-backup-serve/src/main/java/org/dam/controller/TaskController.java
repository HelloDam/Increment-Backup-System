package org.dam.controller;

import org.dam.common.page.PageResponse;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.BackupTask;
import org.dam.entity.request.BackupTaskRequest;
import org.dam.service.BackupTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private BackupTaskService taskService;

    /**
     * 增添数据
     */
    @PostMapping("/save")
    public Result save(@RequestBody BackupTask backupTask) {
        taskService.save(backupTask);
        return Results.success();
    }

    /**
     * 查询正在处理的任务
     *
     * @return
     */
    @GetMapping("/listProcessingTask")
    public Result listProcessingTask() {
        List<BackupTask> taskList = taskService.listProcessingTask();
        return Results.success(taskList);
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    public Result<PageResponse<BackupTask>> list(@RequestBody BackupTaskRequest request) {
        PageResponse<BackupTask> backupTargetPageResponse = taskService.pageBackupTask(request);
        return Results.success(backupTargetPageResponse);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/removeById")
    public Result removeById(Integer id) {
        taskService.removeById(id);
        return Results.success();
    }

    /**
     * 删除数据
     */
    @PostMapping("/removeByIds")
    public Result removeByIds(@RequestBody List<Long> idList) {
        taskService.removeByIds(idList);
        return Results.success();
    }

    /**
     * 修改数据
     */
    @PostMapping("/update")
    public Result update(@RequestBody BackupTask task) {
        taskService.updateById(task);
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
        return Results.success(taskService.getById(id));
    }
}

