package com.bjpowernode;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CaptchaLoginApplicationTests {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    void test01() {
        String password = "aaa111";
        String encodePassword = passwordEncoder.encode(password); //加密方法
        System.out.println(encodePassword);

        boolean match = passwordEncoder.matches(password, encodePassword); //密码匹配
        System.out.println(match);
    }

    @Test
    void test02() {
        String password = "aaa111";
        for (int i = 0; i < 10; i++) {
            String encodePassword = passwordEncoder.encode(password); //加密方法
            System.out.println(encodePassword);

            boolean match = passwordEncoder.matches(password, encodePassword); //密码匹配
            System.out.println(match);
        }
    }
}