package com.bjpowernode.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import com.bjpowernode.captcha.MyCodeGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller //跳转页面
public class CaptchaController {

    @RequestMapping(value = "/common/captcha")
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //告诉浏览器，我的响应内容类型是图片，jpeg格式的图片
        response.setContentType("image/jpeg");

        //生成的是一个验证码的图片，我们不需要跳转页面，就是把生成的这个图片写出到浏览器就可以，以IO流的方式写出去

        //1、生成这个验证码图片
        //ICaptcha captcha = CaptchaUtil.createCircleCaptcha(90, 30, 4, 0, 1);
        //ICaptcha captcha = CaptchaUtil.createCircleCaptcha(90, 30, new MyCodeGenerator(), 15);

        ICaptcha captcha = CaptchaUtil.createGifCaptcha(90, 30, new MyCodeGenerator(), 10);
        //ICaptcha captcha = CaptchaUtil.createGifCaptcha(90, 30, 4, 10, 0.8f);

        //ICaptcha captcha = CaptchaUtil.createLineCaptcha(90, 30, 4, 10, 1);

        //ICaptcha captcha = CaptchaUtil.createShearCaptcha(90, 30, 4, 2, 1);

        //2、把图片里面的验证码字符串（有几个数字）在后端保存起来，因为后续前端提交过来，在后端需要验证提交的验证码对不对
        request.getSession().setAttribute("captcha", captcha.getCode());

        //3、把生成的验证码图片以io流的方式写出去（写出到浏览器）
        captcha.write(response.getOutputStream());
    }
}
