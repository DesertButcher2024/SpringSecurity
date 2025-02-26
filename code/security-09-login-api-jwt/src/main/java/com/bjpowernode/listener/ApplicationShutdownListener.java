package com.bjpowernode.listener;

import com.bjpowernode.constant.Constant;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ApplicationShutdownListener implements ApplicationListener<ContextClosedEvent> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * spring context关闭时，会触发执行该方法
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("spring context 关闭了......");
        //让登录用户的token失效，怎么失效？拿就是把redis中的token删除
        redisTemplate.delete(Constant.REDIS_TOKEN_KEY);
    }
}
