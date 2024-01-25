package org.dam.controller;

import org.dam.common.page.PageResponse;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.BackupFileHistory;
import org.dam.entity.request.BackupFileHistoryRequest;
import org.dam.service.BackupFileHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/fileHistory")
public class FileHistoryController {

    @Autowired
    private BackupFileHistoryService fileHistoryService;

    /**
     * 增添数据
     */
    @PostMapping("/save")
    public Result save(@RequestBody BackupFileHistory backupFileHistory) {
        fileHistoryService.save(backupFileHistory);
        return Results.success();
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    public Result<PageResponse<BackupFileHistory>> list(@RequestBody BackupFileHistoryRequest request) {
        PageResponse<BackupFileHistory> backupTargetPageResponse = fileHistoryService.pageBackupFileHistory(request);
        return Results.success(backupTargetPageResponse);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/removeById")
    public Result removeById(Integer id) {
        fileHistoryService.removeById(id);
        return Results.success();
    }

    /**
     * 删除数据
     */
    @PostMapping("/removeByIds")
    public Result removeByIds(@RequestBody List<Long> idList) {
        fileHistoryService.removeByIds(idList);
        return Results.success();
    }

    /**
     * 修改数据
     */
    @PostMapping("/update")
    public Result update(@RequestBody BackupFileHistory fileHistory) {
        fileHistoryService.updateById(fileHistory);
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
        return Results.success(fileHistoryService.getById(id));
    }
}

