package com.yzl.alarm.base.utils;

import com.yzl.alarm.base.enums.AlarmInfoEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class AlarmThread implements Runnable{

    private RedisUtil redisUtil;
    private AlarmInfoEnum alarmKey;

    public AlarmThread(RedisUtil redisUtil, AlarmInfoEnum alarmKey) {
        this.redisUtil = redisUtil;
        this.alarmKey = alarmKey;
    }

    @Override
    public void run() {
        try {
            String key = AlarmInfoEnum.getCounterKey(alarmKey);
            redisUtil.incrEx(key,1,alarmKey.getCallInterval(), TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("Alarm counter fail to increment ,reason: ",e);
        }
    }
}
