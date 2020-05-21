package com.changhong.sei.notify.manager;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.mq.MqConsumer;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.SendMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息通知的队列消费者
 */
@Component
public class NotifyConsumer extends MqConsumer {
    @Autowired
    private NotifyManagerContext notifyServiceContext;

    /**
     * 收到的监听消息后的业务处理
     *
     * @param message 队列消息
     */
    @Override
    public void process(String message) {
        String key = getKey();
        LogUtil.bizLog("[{}]收到监听消息, Message: {}", key, message);
        if (StringUtils.isBlank(key) || StringUtils.isBlank(message)) {
            return;
        }
        ResultData<String> result;
        // 执行发送消息
        try {
            // 反序列化
            SendMessage sendMessage = JsonUtils.fromJson(message, SendMessage.class);
            // 发送消息
            result = notifyServiceContext.send(key, sendMessage);
        } catch (Exception e) {
            LogUtil.error("执行发送消息异常！", e);
            result = ResultData.fail(e.getMessage());
        }
        // TODO 后续消息统计
        LogUtil.debug("执行发送消息结果: {}", result);
    }
}
