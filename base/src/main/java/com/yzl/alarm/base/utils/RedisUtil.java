package com.yzl.alarm.base.utils;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
public class RedisUtil {

    private StringRedisTemplate stringRedisTemplate;

    public StringRedisTemplate getStringRedisTemplate() {
        return this.stringRedisTemplate;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 计数
     */
    public void incrEx(String key,int increment,int timeout,TimeUnit timeUnit) {
        if (this.hasKey(key)) {
            this.incrBy(key,increment);
        } else {
            this.setEx(key,"1",timeout, timeUnit);
        }
    }


    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }


    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    public void setEx(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public Long incrBy(String key, long increment) {
        return stringRedisTemplate.opsForValue().increment(key, increment);
    }


}