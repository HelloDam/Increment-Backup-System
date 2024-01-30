package org.dam.controller;

import org.dam.common.page.PageResponse;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.FileMessage;
import org.dam.service.FileMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/fileMessage")
public class FileMessageController {

    @Autowired
    private FileMessageService fileMessageService;

    /**
     * 增添数据
     */
    @PostMapping("/save")
    public Result save(@RequestBody FileMessage backupFile) {
        fileMessageService.save(backupFile);
        return Results.success();
    }

    /**
     * 根据数据源ID和父ID查询出所有 fileMessage 数据
     *
     * @param sourceId
     * @param fatherId
     * @return
     */
    @GetMapping("/listChildrenBySourceIdAndFatherId")
    public Result listChildrenBySourceIdAndFatherId(@RequestParam("sourceId") Long sourceId, @RequestParam("fatherId") Long fatherId) {
        List<FileMessage> messageList = fileMessageService.listChildrenBySourceIdAndFatherId(sourceId, fatherId);
        return Results.success(messageList);
    }

    /**
     * 查询数据
     */
//    @PostMapping("/list")
//    public Result<PageResponse<FileMessage>> list(@RequestBody FileMessageRequest request) {
//        PageResponse<FileMessage> backupTargetPageResponse = fileMessageService.pageFileMessage(request);
//        return Results.success(backupTargetPageResponse);
//    }

    /**
     * 删除数据
     */
    @DeleteMapping("/removeById")
    public Result removeById(Integer id) {
        fileMessageService.removeById(id);
        return Results.success();
    }

    /**
     * 删除数据
     */
    @PostMapping("/removeByIds")
    public Result removeByIds(@RequestBody List<Long> idList) {
        fileMessageService.removeByIds(idList);
        return Results.success();
    }

    /**
     * 修改数据
     */
    @PostMapping("/update")
    public Result update(@RequestBody FileMessage fileMessage) {
        fileMessageService.updateById(fileMessage);
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
        return Results.success(fileMessageService.getById(id));
    }
}

