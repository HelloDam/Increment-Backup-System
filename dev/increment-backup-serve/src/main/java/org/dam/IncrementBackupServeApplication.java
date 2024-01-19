package org.dam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.dam.mapper")
public class IncrementBackupServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncrementBackupServeApplication.class, args);
    }

}
