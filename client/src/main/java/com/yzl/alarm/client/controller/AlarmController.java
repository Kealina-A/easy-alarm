package com.yzl.alarm.client.controller;


import com.yzl.alarm.base.enums.AlarmInfoEnum;
import com.yzl.alarm.base.utils.AlarmThread;
import com.yzl.alarm.base.utils.RedisUtil;
import com.yzl.alarm.base.utils.ThreadPoolUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("alarm")
public class AlarmController {

    private final RedisUtil redisUtil;

    @GetMapping("send")
    public String send() {
        ThreadPoolUtil.getPool().execute(new AlarmThread(redisUtil, AlarmInfoEnum.TEST));
        return "success";
    }


}
