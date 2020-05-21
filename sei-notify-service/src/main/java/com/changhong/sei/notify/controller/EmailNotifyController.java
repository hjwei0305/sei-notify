package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.mq.MqProducer;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.api.EmailNotifyApi;
import com.changhong.sei.notify.dto.EmailAccount;
import com.changhong.sei.notify.dto.EmailMessage;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.manager.ContentBuilder;
import com.changhong.sei.notify.dto.SendMessage;
import com.changhong.sei.notify.manager.email.EmailManager;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <strong>实现功能:</strong>
 * <p>发送电子邮件的API服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-13 14:07
 */
@RestController
@Api(value = "EmailNotifyApi", tags = "发送电子邮件的API服务")
@RequestMapping(path = "emailNotify", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EmailNotifyController implements EmailNotifyApi {
    @Autowired
    private ContentBuilder contentBuilder;
    @Autowired
    private EmailManager emailManager;
    /**
     * 注入消息队列生产者
     */
    @Autowired(required = false)
    private MqProducer mqProducer;

    /**
     * 发送一封电子邮件
     *
     * @param emailMessage 电子邮件消息
     */
    @Override
    public ResultData<String> sendEmail(EmailMessage emailMessage) {
        // 通过模板生成内容
        contentBuilder.build(emailMessage);

        if (Objects.isNull(mqProducer)) {
            // 直接发送
            emailManager.send(emailMessage);
        } else {
            // 队列发送
            EmailAccount sender = emailMessage.getSender();
            List<EmailAccount> receiveAccounts = emailMessage.getReceivers();
            List<UserNotifyInfo> receivers = new ArrayList<>();
            if (receiveAccounts != null && receiveAccounts.size() > 0) {
                UserNotifyInfo info;
                for (EmailAccount account : receiveAccounts) {
                    info = UserNotifyInfo.builder()
                            .setUserName(account.getName())
                            .setEmail(account.getAddress());
                    receivers.add(info);
                }
            }

            SendMessage sendMessage = SendMessage.builder()
                    .setContent(emailMessage.getContent())
                    .setSubject(emailMessage.getSubject())
                    .setSender(UserNotifyInfo.builder().setUserName(sender.getName()).setEmail(sender.getAddress()))
                    .setReceivers(receivers);

            // JSON序列化
            String message = JsonUtils.toJson(sendMessage);
            mqProducer.send(NotifyType.EMAIL.name(), message);
        }
        return ResultData.success("OK");
    }
}
