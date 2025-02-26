package com.bjpowernode.captcha;

import cn.hutool.captcha.generator.CodeGenerator;

import java.util.Random;

/**
 * 自定义验证码字符串
 *
 */
public class MyCodeGenerator implements CodeGenerator {

    @Override
    public String generate() {
        int code = 1000 + new Random().nextInt(9000); // 0 - 8999 => [1000 , 9999]
        return String.valueOf(code);
    }

    @Override
    public boolean verify(String code, String userInputCode) {
        return false;
    }
}
