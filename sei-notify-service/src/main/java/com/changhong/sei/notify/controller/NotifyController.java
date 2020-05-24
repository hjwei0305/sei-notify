package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.mq.MqProducer;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.exception.ServiceException;
import com.changhong.sei.notify.api.NotifyApi;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.SendMessage;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.manager.ContentBuilder;
import com.changhong.sei.notify.service.cust.BasicIntegration;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <strong>实现功能:</strong>
 * <p>平台消息通知服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-13 16:45
 */
@RestController
@Api(value = "NotifyApi", tags = "平台消息通知API服务")
@RequestMapping(path = "notify", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NotifyController implements NotifyApi {
    @Autowired
    private BasicIntegration basicIntegration;
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
    public ResultData<String> send(NotifyMessage message) {
        //检查消息通知方式
        if (message == null || CollectionUtils.isEmpty(message.getNotifyTypes())) {
            // 发送的消息类型以及内容不能为空
            return ResultData.fail("00022");
        }
        //检查收件人是否存在
        List<String> receiverIds = message.getReceiverIds();
        if (CollectionUtils.isEmpty(receiverIds)) {
            // 发送消息的收件人不能为空
            return ResultData.fail("00023");
        }
        Set<String> userIds = new HashSet<>(receiverIds);
        if (StringUtils.isNotBlank(message.getSenderId())) {
            userIds.add(message.getSenderId());
        }
        if (CollectionUtils.isEmpty(userIds)) {
            // 发送消息的收件人不能为空
            return ResultData.fail("00023");
        }
        // 调用基础服务，获取用户的消息通知信息
        ResultData<List<UserNotifyInfo>> userInfoResult = basicIntegration.findNotifyInfoByUserIds(new ArrayList<>(userIds));
        if (userInfoResult.failed()) {
            // 记录异常日志
            LogUtil.error(userInfoResult.getMessage(), new ServiceException("调用基础服务，获取用户的消息通知信息异常！"));
            return ResultData.fail("00025");
        }
        List<UserNotifyInfo> userInfos = userInfoResult.getData();
        // 生成消息
        contentBuilder.build(message);
        //消息主题
        String subject = message.getSubject();
        //消息内容
        String content = message.getContent();
        UserNotifyInfo sender = null;
        List<UserNotifyInfo> receivers = new ArrayList<>();
        for (UserNotifyInfo info : userInfos) {
            if (StringUtils.isNotBlank(message.getSenderId()) && Objects.equals(info.getUserId(), message.getSenderId())) {
                sender = info;
            } else {
                receivers.add(info);
            }
        }
        //判断是否发送给发件人
        if (message.isCanToSender() && Objects.nonNull(sender)) {
            if (receiverIds.contains(sender.getUserId())) {
                receivers.add(sender);
            }
        }
        if (CollectionUtils.isEmpty(receivers)) {
            // 发送消息的收件人不存在
            return ResultData.fail("00024");
        }
        // 构造统一发送的消息
        SendMessage sendMessage = new SendMessage();
        sendMessage.setSubject(subject);
        sendMessage.setContent(content);
        sendMessage.setSender(sender);
        sendMessage.setReceivers(receivers);

        // 去重
        Set<NotifyType> notifyTypeSet = new HashSet<>(message.getNotifyTypes());
        // 循环通知方式，循环发送
        for (NotifyType notifyType : notifyTypeSet) {
            String sendJson = JsonUtils.toJson(sendMessage);
            mqProducer.send(notifyType.name(), sendJson);
        }
        return ResultData.success("ok");
    }
}
