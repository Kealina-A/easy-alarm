# easy-alarm

> 简易的告警程序，逻辑简单，简单的策略，适用于小型程序,只需要 redis
## 环境
- 语言: jdk 8
- 数据存储: redis

## 模块说明
* base： 公用模块
* client： 客户端触发
* server： 告警服务

## 使用
0. 创建事件：　在AlarmInfoEnum 中添加事件，如：   `TEST("TEST","这是测试内容~",1,1,1)`

1. 触发事件：　
`redisUtil.incrEx(key,1,alarmKey.getCallInterval(), TimeUnit.MINUTES)`;

## 原理
1. 客户端：触发事件，通过对某一个`告警事件`进行`计数`(使用redis),使用　redis 的超时设置
2. 服务端：对`告警事件`进行检测,当在一段时间内触发次数超过设置的`阀值`，发送告警信息，
标记已经发送过，过期时间为设置的参数`间隔发送消息时间`

### 核心代码
client
```$java
//　设置告警事件并记数 alarmKey.getCallInterval()为该事件的过期记数时间
redisUtil.incrEx(key,1,alarmKey.getCallInterval(), TimeUnit.MINUTES);
```
server
```$java
for (AlarmInfoEnum alarmInfo : allAlarmInfoEnums) {

    String counterKey = AlarmInfoEnum.getCounterKey(alarmInfo);
    String markKey = AlarmInfoEnum.getMarkKey(alarmInfo);

    // 是否有存在该告警事件　或者　是否已经标记过发送　
    if (!redisUtil.hasKey(counterKey) || redisUtil.hasKey(markKey)) {
        continue;
    }
    Integer counter = Integer.valueOf(redisUtil.get(counterKey));
    if (counter >= alarmInfo.getThreshold()) {
        alarmService.sendByServerJ(alarmInfo.getMsg()); 
        // 标记已经发送过
        redisUtil.setEx(markKey,"true",alarmInfo.getSendInterval(), TimeUnit.MINUTES);
    }
}
```

可参考 base.enums.AlarmInfoEnum 注释
```$java

重要参数说明如下:

eventName：　告警事件名，不重复即可
msg：　消息内容尽可能的简洁，不超过256个字符
threshold ：　触发阀值 >=
sendInterval ：　间隔发送消息时间 时间单位：分钟
callInterval ：　统计触发个数的间隔时间 时间单位：分钟

```
