package com.bjpowernode.config;

import com.bjpowernode.filter.TokenFilter;
import com.bjpowernode.handler.AppLogoutSuccessHandler;
import com.bjpowernode.handler.MyAccessDeniedHandler;
import com.bjpowernode.handler.MyAuthenticationFailureHandler;
import com.bjpowernode.handler.MyAuthenticationSuccessHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableMethodSecurity //开启方法上的注解权限检查
@Configuration //配置spring的容器，类似spring.xml文件一样
public class SecurityConfig {

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    private AppLogoutSuccessHandler appLogoutSuccessHandler;

    @Resource
    private TokenFilter tokenFilter;

    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;

    /**
     * <bean id="passwordEncoder" class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder"> </bean>
     *
     * @return
     */
    @Bean  //配置一个spring的bean， bean的id就是方法名，bean的class就是方法的返回类型
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean //配置跨域
    public CorsConfigurationSource configurationSource() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        //跨域配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*")); //允许任何来源，http://localhost:10492/
        corsConfiguration.setAllowedMethods(Arrays.asList("*")); //允许任何请求方法，post、get、put、delete
        corsConfiguration.setAllowedHeaders(Arrays.asList("*")); //允许任何的请求头 (jwt)

        //注册跨域配置
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration); //  /api/user,  /api/user/12082
        return urlBasedCorsConfigurationSource;
    }

    //配置spring security框架的一些行为（配置我们自己的登录页，不使用框架默认的登录页）
    //但是当你配置了SecurityFilterChain这个Bean之后，Spring security框架的某些默认行为就弄丢了（失效了），此时你需要加回来（捡回来）
    @Bean //安全过滤器链Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource configurationSource) throws Exception { //httpSecurity是方法参数注入Bean
        //在spring security框架开发时，创建SecurityFilterChain这个Bean，不是直接new DefaultSecurityFilterChain
        //return new DefaultSecurityFilterChain();

        return httpSecurity
                //配置我们自己的登录页
                .formLogin( (formLogin) -> {
                    // 框架默认接收登录提交请求的地址是 /login，但是我们把它给弄丢了，需要捡回来
                    formLogin.loginProcessingUrl("/user/login") //登录的账号密码往哪个地址提交
                            .successHandler(myAuthenticationSuccessHandler) //登录成功后执行该handler
                            .failureHandler(myAuthenticationFailureHandler);// 登录失败后执行该handler
                    // 在访问 /user/login 地址之前，对于后端应用来说，没有上一个地址（原地），那默认是跳转到项目的根路径斜杆/
                })

                .logout((logout) -> {
                    logout.logoutUrl("/user/logout") //退出请求提交到哪个地址
                            .logoutSuccessHandler(appLogoutSuccessHandler); //退出成功后执行该handler
                            //退出失败呢？Spring Security框架没有退出失败的处理
                })

                //把所有接口都会进行登录状态检查的默认行为，再加回来
                .authorizeHttpRequests( (authorizeHttpRequests) -> {
                    authorizeHttpRequests
                            .anyRequest().authenticated(); //除了上面的特殊情况外，其他任何对后端接口的请求，都需要认证（登录）后才能访问
                })

                .csrf( (csrf) -> {
                    //禁用csrf跨站请求伪造，禁用之后，肯定就不安全了，有csrf网络攻击的风险，后续加入jwt是可以防御的
                    csrf.disable();
                })

                .cors( (cors) -> { //允许前端跨域访问
                    cors.configurationSource(configurationSource);
                })

                .sessionManagement((sessionManagement) -> {
                    //session创建策略 (无session状态)
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })

                //在登录Filter后面加入我们的token验证的Filter
                .addFilterBefore(tokenFilter, LogoutFilter.class)

                .exceptionHandling((exceptionHandling) -> {
                    exceptionHandling.accessDeniedHandler(myAccessDeniedHandler); //没有权限的时候，执行该handler
                })

                .build();
    }

    //前端：前后端不分离  +   前后端分离
    //前后端不分离: JSP（几乎完全过时了）、模版技术Thymeleaf、FreeMarker、Velocity (这几个也几乎过时了)
    //前后端分离：比较流行的，代表技术：Vue.js、React.js
}