package com.changhong.sei.notify.manager;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
     * 注入所有实现了策略接口(NotifyService)的Bean
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
    public ResultData<String> send(String notifyType, SendMessage message){
        NotifyManager notifyManager = strategyMap.get(notifyType);
        if (Objects.nonNull(notifyManager)) {
            return notifyManager.send(message);
        } else {
            return ResultData.fail(notifyType + " 通知类型未定义");
        }
    }
}
