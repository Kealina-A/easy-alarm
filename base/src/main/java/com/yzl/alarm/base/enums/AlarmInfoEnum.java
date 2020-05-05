package com.yzl.alarm.base.enums;

import cn.hutool.core.util.RandomUtil;
import lombok.Getter;

import java.util.EnumSet;

@Getter
public enum AlarmInfoEnum {
    TEST("TEST","这是测试内容~",1,1,1),
    ;

    /**
     * 告警事件名，不重复即可
     */
    private String eventName;
    /**
     * 消息内容尽可能的简洁，不超过256个字符
     */
    private String msg;
    /**
     *　触发阀值 >=
     */
    private int threshold = 10;
    /**
     *　间隔发送消息时间 时间单位：分钟
     */
    private int sendInterval = 10;
    /**
     * 统计触发个数的间隔时间 时间单位：分钟
     */
    private int callInterval = 5;

    public static final EnumSet<AlarmInfoEnum> ALL_ALARM_KEY_ENUMS = EnumSet.allOf(AlarmInfoEnum.class);

    /**
     * 格式　alarm:counter:event
     */
    public static String ALARM_PREFIX = "ALARM:";
    public static String COUNTER_TYPE = ALARM_PREFIX+"COUNTER:";
    public static String SENT_MARK_TYPE = ALARM_PREFIX+"SENT_MARK:";


    AlarmInfoEnum(String eventName, String msg, int threshold, int sendInterval, int callInterval) {
        this.eventName = eventName;
        this.msg = msg;
        this.threshold = threshold;
        this.sendInterval = sendInterval;
        this.callInterval = callInterval;
    }

    public static String getCounterKey(AlarmInfoEnum e) {
        return COUNTER_TYPE+e.getEventName();
    }
    public static String getMarkKey(AlarmInfoEnum e) {
        return SENT_MARK_TYPE+e.getEventName();
    }

    /**
     * 为了区分不同的消息内容
     */
    public String getMsg() {
        return "代号"+ RandomUtil.randomInt(65535) +": "+msg ;
    }
}
