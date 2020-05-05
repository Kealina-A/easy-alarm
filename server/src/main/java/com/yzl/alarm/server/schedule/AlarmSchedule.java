package com.yzl.alarm.server.schedule;

import com.yzl.alarm.base.enums.AlarmInfoEnum;
import com.yzl.alarm.base.utils.RedisUtil;
import com.yzl.alarm.server.service.AlarmService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class AlarmSchedule {

    private final RedisUtil redisUtil;
    private final AlarmService alarmService;

    @Scheduled(fixedDelay = 1000)
    public void main (){
        EnumSet<AlarmInfoEnum> allAlarmInfoEnums = AlarmInfoEnum.ALL_ALARM_KEY_ENUMS;
        for (AlarmInfoEnum alarmInfo : allAlarmInfoEnums) {

            String counterKey = AlarmInfoEnum.getCounterKey(alarmInfo);
            String markKey = AlarmInfoEnum.getMarkKey(alarmInfo);

            if (!redisUtil.hasKey(counterKey) || redisUtil.hasKey(markKey)) {
                continue;
            }
            Integer counter = Integer.valueOf(redisUtil.get(counterKey));
            if (counter >= alarmInfo.getThreshold()) {
                alarmService.sendByServerJ(alarmInfo.getMsg());
                redisUtil.setEx(markKey,"true",alarmInfo.getSendInterval(), TimeUnit.MINUTES);
            }
        }
    }
}
