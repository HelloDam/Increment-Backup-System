package org.dam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dam.entity.BackupTask;
import org.dam.service.BackupTaskService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {IncrementBackupServeApplication.class})
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IncrementBackupServeApplicationTests {

    @Autowired
    private BackupTaskService backupTaskService;
    @Test
    void contextLoads() {
    }

//    @Test
//    void dasd(){
//        BackupTask backupTask = new BackupTask("dasdas", "dsadas",
//                10, 0, 10L, 0L, 0,"0.0");
//        backupTaskService.save(backupTask);
//    }

    @Test
    void dashidas(){
        QueryWrapper<BackupTask> backupTaskQueryWrapper = new QueryWrapper<>();
            backupTaskQueryWrapper.ne("backup_status", 2);
        List<BackupTask> taskList = backupTaskService.list(backupTaskQueryWrapper);
    }

}
