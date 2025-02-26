package com.bjpowernode.handler;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.bjpowernode.constant.Constant;
import com.bjpowernode.entity.TUser;
import com.bjpowernode.result.R;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 登录成功，会执行该handler
 *
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    //直接注入该RedisTemplate就可以操作redis了，这个类的泛型分别是放入redis中的key的类型和value的类型
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //生成jwt (token)
        TUser tUser = (TUser) authentication.getPrincipal();
        String userJSON = JSONUtil.toJsonStr(tUser);
        String token = JWTUtil.createToken(Map.of("user", userJSON), Constant.SECRET.getBytes());

        //把token写入redis (五种值的结构类型：string，hash，list，set，zset)
        redisTemplate.opsForValue(); //操作string
        redisTemplate.opsForHash(); //操作hash
        redisTemplate.opsForList(); //操作list
        redisTemplate.opsForSet(); //操作set
        redisTemplate.opsForZSet(); //操作zset

        //token适合使用string或者hash结构进行存储
        redisTemplate.opsForHash().put(Constant.REDIS_TOKEN_KEY, String.valueOf(tUser.getId()), token);

        //测试一下，怎么把redis的值取出来
        //String redisToken = (String)redisTemplate.opsForHash().get(Constant.REDIS_TOKEN_KEY, tUser.getId());

        //采用构建器模式，链式编程创建一个R对象
        R result = R.builder().code(200).msg("登录成功").info(token).build();

        //hutool工具包，把R对象转成json字符串
        String json = JSONUtil.toJsonStr(result);

        //把json写出去，写出到浏览器客户端
        response.getWriter().write(json);
    }
}
