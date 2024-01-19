package org.dam.controller;

import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.BackupFile;
import org.dam.entity.BackupFileHistory;
import org.dam.service.BackupFileHistoryService;
import org.dam.service.BackupFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/insertTarget")
    public Result insertTarget(long backupSourceId, String targetRootPath) {
        BackupFileHistory fileHistory = new BackupFileHistory();
//        backupFile.setBackupSourceId(backupSourceId);
//        backupFile.setFilePath();
//        backupFile.setBackupNum();
//        backupFile.setLastBackupTime();
        fileHistoryService.save(fileHistory);
        return Results.success();
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    public Result<PageResponse<BackupFileHistory>> list(PageRequest pageRequest) {
        return Results.success(fileHistoryService.pageBackupFile(pageRequest));
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
     * 修改数据
     */
    @PostMapping("/update")
    public Result update(BackupFileHistory fileHistory) {
        fileHistoryService.updateById(fileHistory);
        return Results.success();
    }
}
