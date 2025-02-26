package com.bjpowernode.config;

import com.bjpowernode.filter.CaptchaFilter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity //开启方法上的注解权限检查
@Configuration //配置spring的容器，类似spring.xml文件一样
public class SecurityConfig {

    @Resource
    private CaptchaFilter captchaFilter;

    /**
     * <bean id="passwordEncoder" class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder"> </bean>
     *
     * @return
     */
    @Bean  //配置一个spring的bean， bean的id就是方法名，bean的class就是方法的返回类型
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //配置spring security框架的一些行为（配置我们自己的登录页，不使用框架默认的登录页）
    //但是当你配置了SecurityFilterChain这个Bean之后，Spring security框架的某些默认行为就弄丢了（失效了），此时你需要加回来（捡回来）
    @Bean //安全过滤器链Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { //httpSecurity是方法参数注入Bean
        //在spring security框架开发时，创建SecurityFilterChain这个Bean，不是直接new DefaultSecurityFilterChain
        //return new DefaultSecurityFilterChain();

        return httpSecurity

                //配置我们自己的登录页
                .formLogin( (formLogin) -> {
                    // 框架默认接收登录提交请求的地址是 /login，但是我们把它给弄丢了，需要捡回来
                    formLogin.loginProcessingUrl("/user/login") //登录的账号密码往哪个地址提交
                            .loginPage("/toLogin") //定制登录页（Thymeleaf页面）
                            //默认情况下，spring security框架登录成功后是跳转到登录前所访问的那个地址（回到原地）
                            .successForwardUrl("/welcome"); //定制登录成功跳转到哪个地址/页面
                })

                //把所有接口都会进行登录状态检查的默认行为，再加回来
                .authorizeHttpRequests( (authorizeHttpRequests) -> {
                    authorizeHttpRequests
                            .requestMatchers("/toLogin", "/common/captcha").permitAll() //特殊情况的设置，permitAll允许不登录就可以访问
                            .anyRequest().authenticated(); //除了上面的特殊情况外，其他任何对后端接口的请求，都需要认证（登录）后才能访问
                })

                //验证码filter加在接收登录账号密码的filter之前
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    //前端：前后端不分离  +   前后端分离
    //前后端不分离: JSP（几乎完全过时了）、模版技术Thymeleaf、FreeMarker、Velocity (这几个也几乎过时了)
    //前后端分离：比较流行的，代表技术：Vue.js、React.js
}