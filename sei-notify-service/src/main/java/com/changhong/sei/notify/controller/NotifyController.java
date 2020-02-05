package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.mq.MqProducer;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.api.NotifyApi;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.service.ContentBuilder;
import com.changhong.sei.notify.service.SendMessage;
import com.changhong.sei.notify.service.client.UserNotifyInfoClient;
import com.chonghong.sei.exception.ServiceException;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <strong>实现功能:</strong>
 * <p>平台消息通知服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-13 16:45
 */
@Service
@Api(value = "NotifyApi", tags = "平台消息通知API服务")
public class NotifyController implements NotifyApi {
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
        if (CollectionUtils.isEmpty(userIds)){
            return;
        }
        // 调用基础服务，获取用户的消息通知信息
        ResultData<List<UserNotifyInfo>> userInfoResult = userNotifyInfoClient.findNotifyInfoByUserIds(new ArrayList<>(userIds));
        if (userInfoResult.failed()){
            // 记录异常日志
            LogUtil.error(userInfoResult.getMessage(), new ServiceException("调用基础服务，获取用户的消息通知信息异常！"));
            return;
        }
        List<UserNotifyInfo> userInfos = userInfoResult.getData();
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
        // 构造统一发送的消息
        SendMessage sendMessage = new SendMessage();
        sendMessage.setSubject(subject);
        sendMessage.setContent(content);
        sendMessage.setSender(sender);
        sendMessage.setReceivers(receivers);
        // 循环通知方式，循环发送
        for (NotifyType notifyType: message.getNotifyTypes()){
            String sendJson = JsonUtils.toJson(sendMessage);
            mqProducer.send(notifyType.name(), sendJson);
        }
    }
}
