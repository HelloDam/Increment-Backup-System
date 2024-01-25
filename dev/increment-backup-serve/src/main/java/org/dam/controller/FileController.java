package org.dam.controller;

import org.dam.common.page.PageRequest;
import org.dam.common.page.PageResponse;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.BackupFile;
import org.dam.entity.BackupTarget;
import org.dam.service.BackupFileService;
import org.dam.service.BackupTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private BackupFileService fileService;

    /**
     * 增添数据
     */
    @GetMapping("/insertTarget")
    public Result insertTarget(long backupSourceId, String targetRootPath) {
        BackupFile backupFile = new BackupFile();
//        backupFile.setBackupSourceId(backupSourceId);
//        backupFile.setFilePath();
//        backupFile.setBackupNum();
//        backupFile.setLastBackupTime();

        fileService.save(backupFile);
        return Results.success();
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    public Result<PageResponse<BackupFile>> list(PageRequest pageRequest) {
        return Results.success(fileService.pageBackupFile(pageRequest));
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/removeById")
    public Result removeById(Integer id) {
        fileService.removeById(id);
        return Results.success();
    }

    /**
     * 修改数据
     */
    @PostMapping("/update")
    public Result update(BackupFile file) {
        fileService.updateById(file);
        return Results.success();
    }
}
