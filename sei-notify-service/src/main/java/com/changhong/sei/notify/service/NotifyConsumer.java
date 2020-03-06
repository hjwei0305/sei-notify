package com.changhong.sei.notify.service;

import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.mq.MqConsumer;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.EmailMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.changhong.sei.notify.service.HelloService.HELLO_MQ_KEY;
import static com.changhong.sei.notify.controller.EmailNotifyController.EMAIL_MQ_KEY;

/**
 * 消息通知的队列消费者
 */
@Component
public class NotifyConsumer extends MqConsumer {
    @Autowired
    private EmailService emailManager;
    @Autowired
    private NotifyServiceContext notifyServiceContext;

    /**
     * 收到的监听消息后的业务处理
     *
     * @param message 队列消息
     */
    @Override
    public void process(String message) {
        LogUtil.bizLog("执行收到的监听消息后的业务处理。message=" + message);
        if (StringUtils.isBlank(getKey()) || StringUtils.isBlank(message)) {
            return;
        }
        // 处理say hello 的消息
        if (getKey().equals(HELLO_MQ_KEY)) {
            LogUtil.bizLog("执行业务逻辑：你好！" + message);
            return;
        }
        // 执行发送邮件
        if (getKey().equals(EMAIL_MQ_KEY)) {
            try {
                // 反序列化
                EmailMessage emailMessage = JsonUtils.fromJson(message, EmailMessage.class);
                // 发送邮件
                emailManager.send(emailMessage);
            } catch (Exception e) {
                LogUtil.error("执行发送邮件异常！", e);
            }
            return;
        }
        // 执行发送消息
        try {
            // 反序列化
            SendMessage sendMessage = JsonUtils.fromJson(message, SendMessage.class);
            // 发送消息
            notifyServiceContext.send(getKey(), sendMessage);
        } catch (Exception e) {
            LogUtil.error("执行发送消息异常！", e);
        }
    }
}
