package com.bjpowernode.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //这个注解，返回字符串或者json， @Controller注解跳转到页面
public class UserController {

    //体验：加入了Spring Security之后，所有的controller接口都需要登录后才能访问，如果没有登录，它会自动跳转到登录页去登录

    // http://localhost:8080/  ->  http://localhost:8080/login
    @RequestMapping(value = "/")
    public String index() {
        return "Index, Spring Security."; //返回字符串
    }

    // http://localhost:8080/hello  ->  http://localhost:8080/login
    @RequestMapping(value = "/hello")
    public String hello() {
        return "Hello, Spring Security."; //返回字符串
    }

}
