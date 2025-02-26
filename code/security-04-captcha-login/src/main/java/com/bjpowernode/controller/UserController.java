package com.bjpowernode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //跳转到页面上
public class UserController {

    // http://localhost:8080/  --> 没登录 --> http://localhost:8080/toLogin --> login.html页面 --> 查询数据库登录
    // http://localhost:8080/  --> 登录了 --> 返回"Welcome to Spring Security."字符串

    @RequestMapping(value = "/")
    public @ResponseBody String index() { //@ResponseBody注解，表示方法返回字符串或者json
        return "Welcome to Spring Security.";
    }

    @RequestMapping(value = "/toLogin")
    public String toLogin() {
        return "login";
    }
}
