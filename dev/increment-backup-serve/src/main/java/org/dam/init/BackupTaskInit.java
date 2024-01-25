package org.dam.init;

import lombok.extern.slf4j.Slf4j;
import org.dam.service.BackupTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author dam
 * @create 2024/1/25 19:29
 */
@Component
@Slf4j
public class BackupTaskInit implements CommandLineRunner {
    @Autowired
    private BackupTaskService backupTaskService;
    @Override
    public void run(String... args) throws Exception {
        log.info("项目启动成功，执行初始化，将没有完成的备份任务设置为失败状态");
        backupTaskService.updateNotFinishedTask();
    }
}
