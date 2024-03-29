package org.dam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dam.common.result.Result;
import org.dam.common.result.Results;
import org.dam.entity.BackupFile;
import org.dam.service.BackupFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/compress")
//@CrossOrigin
public class CompressController {
    @Autowired
    private BackupFileService backupFileService;

    /**
     * 根据ID对指定的fileMessage进行解压操作，如果是目录，则递归解压其所有子文件
     */
    @GetMapping("/unCompress/{fileMessageId}")
    public Result unCompress(@PathVariable Long fileMessageId) {
        backupFileService.unCompress(fileMessageId);
        return Results.success();
    }

    /**
     * 下载解压后的文件
     *
     * @param fileMessageId
     * @return
     */
    @GetMapping("/downloadUnCompressFile/{fileMessageId}")
    public void downloadUnCompressFile(@PathVariable Long fileMessageId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        /// 将文件进行解压，获取解压之后的字节
        BackupFile fileMessage = backupFileService.getOne(new QueryWrapper<BackupFile>().eq("id", fileMessageId));
        byte[] bytes = backupFileService.downloadUnCompressFile(fileMessage);

        /// 设置response 响应头
        // 字符编码
        response.setCharacterEncoding("UTF-8");
        // 二进制传输数据
        response.setContentType("multipart/form-data");
        // 设置响应头
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + URLEncoder.encode(fileMessage.getFileName(), "UTF-8"));

        /// 执行写出操作
        OutputStream out = response.getOutputStream();
        out.write(bytes, 0, bytes.length);
        out.flush();
        out.close();
    }
}
