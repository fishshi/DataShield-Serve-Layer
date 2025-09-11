package com.datashield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot 启动类
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class DatashieldApplication {
    public static void main(String[] args) {
        SpringApplication.run(DatashieldApplication.class, args);
    }
}
