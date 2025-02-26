package com.bjpowernode.controller;

import com.bjpowernode.entity.User2;
import com.bjpowernode.util.LoginInfoUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller //跳转到页面上
public class UserController {

    // http://localhost:8080/  --> 没登录 --> http://localhost:8080/toLogin --> login.html页面 --> 查询数据库登录
    // http://localhost:8080/  --> 登录了 --> 返回"Welcome to Spring Security."字符串

    @RequestMapping(value = "/")
    public @ResponseBody String index() { //@ResponseBody注解，表示方法返回字符串或者json
        //如何拿到登录人的完整信息，比如userId、phone、email、loginAct ......
        return "Welcome to Spring Security.";
    }

    @RequestMapping(value = "/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "/welcome")
    public @ResponseBody Object welcome(Principal principal) { //@ResponseBody注解，表示方法返回字符串或者json
        //如何拿到登录人的完整信息，比如userId、phone、email、loginAct ......
        return principal;
    }

    @RequestMapping(value = "/welcome2")
    public @ResponseBody Object welcome2(Authentication authentication) { //@ResponseBody注解，表示方法返回字符串或者json
        //如何拿到登录人的完整信息，比如userId、phone、email、loginAct ......
        return authentication;
    }

    @RequestMapping(value = "/welcome3")
    public @ResponseBody Object welcome3(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) { //@ResponseBody注解，表示方法返回字符串或者json
        //如何拿到登录人的完整信息，比如userId、phone、email、loginAct ......
        return usernamePasswordAuthenticationToken;
    }

    @RequestMapping(value = "/welcome4")
    public @ResponseBody Object welcome4() { //@ResponseBody注解，表示方法返回字符串或者json
        //如何拿到登录人的完整信息，比如userId、phone、email、loginAct ......
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping(value = "/welcome5")
    public @ResponseBody Object welcome5() { //@ResponseBody注解，表示方法返回字符串或者json
        //如何拿到登录人的完整信息，比如userId、phone、email、loginAct ......
        return LoginInfoUtil.getCurrentLoginUser();
    }

    @RequestMapping(value = "/test")
    public @ResponseBody Object test() { //@ResponseBody注解，表示方法返回字符串或者json
        //如何拿到登录人的完整信息，比如userId、phone、email、loginAct ......
        User2 user2 = new User2();
        return user2; //底层使用jackson把user2对象转成json
    }
}
