package com.changhong.sei.notify.service;

import com.changhong.sei.core.mq.MqProducer;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.api.NotifyService;
import com.changhong.sei.notify.dto.*;
import com.changhong.sei.notify.manager.ContentBuilder;
import com.changhong.sei.notify.manager.client.UserNotifyInfoClient;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.changhong.sei.notify.service.EmailNotifyServiceImpl.EMAIL_MQ_KEY;

/**
 * <strong>实现功能:</strong>
 * <p>平台消息通知服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-13 16:45
 */
@Service
@Api(value = "NotifyService", tags = "平台消息通知API服务")
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private UserNotifyInfoClient userNotifyInfoClient;
    @Autowired
    private ContentBuilder contentBuilder;
    /**
     * 注入消息队列生产者
     */
    @Autowired
    private MqProducer mqProducer;
    /**
     * 发送平台消息通知
     *
     * @param message 平台消息
     */
    @Override
    public void send(NotifyMessage message) {
        //检查消息通知方式
        if (message == null || CollectionUtils.isEmpty(message.getNotifyTypes())) {
            return;
        }
        //检查收件人是否存在
        if (CollectionUtils.isEmpty(message.getReceiverIds())) {
            return;
        }
        List<String> receiverIds = message.getReceiverIds();
        Set<String> userIds = new HashSet<>(receiverIds);
        if (StringUtils.isNotBlank(message.getSenderId())) {
            userIds.add(message.getSenderId());
        }
        // 调用基础服务，获取用户的消息通知信息
        List<UserNotifyInfo> userInfos = userNotifyInfoClient.findNotifyInfoByUserIds(new ArrayList<>(userIds));
        if (CollectionUtils.isEmpty(userIds)){
            return;
        }
        // 生成消息
        contentBuilder.build(message);
        //消息主题
        String subject = message.getSubject();
        //消息内容
        String content = message.getContent();
        UserNotifyInfo sender=null;
        List<UserNotifyInfo> receivers = new ArrayList<>();
        for (UserNotifyInfo info : userInfos) {
            if (StringUtils.isNotBlank(message.getSenderId()) && Objects.equals(info.getUserId(), message.getSenderId())) {
                sender = info;
            } else {
                receivers.add(info);
            }
        }
        //判断是否发送给发件人
        if (message.isCanToSender()&&Objects.nonNull(sender)){
            if (receiverIds.contains(sender.getUserId())){
                receivers.add(sender);
            }
        }
        if (CollectionUtils.isEmpty(receivers)) {
            return;
        }
        // 循环通知方式，循环发送
        for (NotifyType notifyType: message.getNotifyTypes()){
            switch (notifyType){
                // 发送邮件
                case Email:
                    // 构造邮件消息
                    EmailMessage emailMsg = new EmailMessage();
                    if (Objects.nonNull(sender)&&StringUtils.isNotBlank(sender.getEmail())){
                        emailMsg.setSender(new EmailAccount(sender.getUserName(),sender.getEmail()));
                    }
                    emailMsg.setSubject(subject);
                    emailMsg.setContent(content);
                    List<EmailAccount> emailAccounts = new ArrayList<>();
                    receivers.forEach((r)->emailAccounts.add(new EmailAccount(r.getUserName(),r.getEmail())));
                    emailMsg.setReceivers(emailAccounts);
                    // 提交到发送邮件队列
                    // JSON序列化
                    String messageJson = JsonUtils.toJson(emailMsg);
                    mqProducer.send(EMAIL_MQ_KEY, messageJson);
                    break;
                case Sms:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + notifyType);
            }
        }
    }
}
