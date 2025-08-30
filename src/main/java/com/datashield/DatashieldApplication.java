package com.datashield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * SpringBoot 启动类
 */
@SpringBootApplication
@EnableCaching
public class DatashieldApplication {
    public static void main(String[] args) {
        SpringApplication.run(DatashieldApplication.class, args);
    }
}
