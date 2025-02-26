package com.bjpowernode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = {"com.bjpowernode.mapper"})
@SpringBootApplication
public class DbLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbLoginApplication.class, args);
    }

}
