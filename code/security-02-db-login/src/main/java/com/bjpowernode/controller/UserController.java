package com.bjpowernode.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //返回字符串或json，这样我们测试更便捷
public class UserController {

    // http://localhost:8080/  --> 没登录 --> http://localhost:8080/login  --> 查询数据库登录
    // http://localhost:8080/  --> 登录了 --> 返回"Welcome to Spring Security."字符串
    @RequestMapping(value = "/")
    public String index() {
        return "Welcome to Spring Security.";
    }
}
