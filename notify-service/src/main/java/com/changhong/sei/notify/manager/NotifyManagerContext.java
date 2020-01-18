package com.changhong.sei.notify.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <strong>实现功能:</strong>
 * <p>发送消息统一接口的上下文环境</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-18 20:36
 */
@Component
public class NotifyManagerContext {
    // 策略映射map
    private static final Map<String, NotifyManager> strategyMap = new HashMap<>();

    /**
     * 注入所有实现了策略接口(NotifyManager)的Bean
     * @param strategyMap 策略映射
     */
    @Autowired
    public NotifyManagerContext(Map<String, NotifyManager> strategyMap) {
        NotifyManagerContext.strategyMap.clear();
        strategyMap.forEach(NotifyManagerContext.strategyMap::put);
    }

    /**
     * 发送消息
     * @param notifyType 消息类型标识
     * @param message 发送的消息
     */
    public void send(String notifyType, SendMessage message){
        strategyMap.get(notifyType).send(message);
    }
}
