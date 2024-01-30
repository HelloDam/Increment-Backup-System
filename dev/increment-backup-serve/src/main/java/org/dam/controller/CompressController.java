package org.dam.controller;

import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.service.FileMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/compress")
public class CompressController {
    @Autowired
    private FileMessageService fileMessageService;

    /**
     * 根据ID对指定的fileMessage进行解压操作，如果是目录，则递归解压其所有子文件
     */
    @GetMapping("/unCompress/{fileMessageId}")
    public Result unCompress(@PathVariable Long fileMessageId) {
        fileMessageService.unCompress(fileMessageId);
        return Results.success();
    }

}
