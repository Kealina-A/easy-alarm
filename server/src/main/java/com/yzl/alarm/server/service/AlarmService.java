package com.yzl.alarm.server.service;

import cn.hutool.http.Header;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import com.yzl.alarm.server.configuration.AlarmProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class AlarmService {

    private final AlarmProperties alarmProperties;

    public String sendByServerJ(String content) {
        String url = alarmProperties.getUrl();
        Map<String,Object> param = new HashMap<>();
        param.put("text",content);
        param.put("desp",content);
        String response = null;
        try {
            response = HttpRequest.get(url)
                    .header(Header.CONTENT_TYPE, "application/json")
                    .timeout(30000)
                    .form(param)
                    .execute()
                    .body();
            log.info("Request serverJ, url:[{}] response: [{}]",url,  response);
        } catch (HttpException e1) {
            log.error("Request serverJ, network error, url:[{}] , reason:[{}]",url,  e1.getMessage());
        } catch (Exception e) {
            log.error("Request serverJ, exception: {}",  e.getMessage());
        }
        return response;
    }

}
