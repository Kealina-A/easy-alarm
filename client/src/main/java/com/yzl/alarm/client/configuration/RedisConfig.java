package com.yzl.alarm.client.configuration;

import com.yzl.alarm.base.utils.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;


@Configuration
@AllArgsConstructor
public class RedisConfig {

    private final StringRedisTemplate redisTemplate;

    @Bean
    public RedisUtil redisUtil () {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setStringRedisTemplate(redisTemplate);
        return redisUtil;
    }

}