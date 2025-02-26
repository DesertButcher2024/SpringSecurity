package com.bjpowernode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = {"com.bjpowernode.mapper"})
@SpringBootApplication
public class CaptchaLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaptchaLoginApplication.class, args);
    }
}
