package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.mq.MqProducer;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.exception.ServiceException;
import com.changhong.sei.notify.dto.*;
import com.changhong.sei.notify.entity.Message;
import com.changhong.sei.notify.entity.MessageHistory;
import com.changhong.sei.notify.manager.ContentBuilder;
import com.changhong.sei.notify.manager.email.EmailManager;
import com.changhong.sei.notify.service.cust.BasicIntegration;
import com.changhong.sei.util.EnumUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

/**
 * <strong>实现功能:</strong>
 * <p>平台消息通知服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-13 16:45
 */
@Service
public class NotifyService {
    @Autowired
    private BasicIntegration basicIntegration;
    @Autowired
    private ContentBuilder contentBuilder;
    @Autowired
    private EmailManager emailManager;
    @Autowired
    private BulletinService service;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageHistoryService historyService;
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
    public ResultData<String> send(NotifyMessage message) {
        //检查消息通知方式
        if (message == null || CollectionUtils.isEmpty(message.getNotifyTypes())) {
            // 发送的消息类型以及内容不能为空
            return ResultData.fail(ContextUtil.getMessage("00022"));
        }

        for (NotifyType notifyType : message.getNotifyTypes()) {
            if (NotifyType.SEI_BULLETIN == notifyType) {
                BulletinDto bulletinDto = new BulletinDto();
                bulletinDto.setSubject(message.getSubject());
                bulletinDto.setContent(message.getContent());
                bulletinDto.setCategory(notifyType);
                bulletinDto.setTargetType(TargetType.SYSTEM);
                bulletinDto.setTargetValue("SYSTEM");
                bulletinDto.setTargetName("SYSTEM");
                bulletinDto.setDocIds(message.getDocIds());
                LocalDate now = LocalDate.now();
                bulletinDto.setEffectiveDate(now);
                bulletinDto.setInvalidDate(now.plusDays(180));

                service.sendBulletin(bulletinDto);

                if (message.getNotifyTypes().size() == 1) {
                    return ResultData.success("OK");
                }
            }
        }

        //检查收件人是否存在
        List<String> receiverIds = message.getReceiverIds();
        if (CollectionUtils.isEmpty(receiverIds)) {
            // 发送消息的收件人不能为空
            return ResultData.fail(ContextUtil.getMessage("00023"));
        }
        Set<String> userIds = new HashSet<>(receiverIds);
        if (StringUtils.isNotBlank(message.getSenderId())) {
            userIds.add(message.getSenderId());
        }
        if (CollectionUtils.isEmpty(userIds)) {
            // 发送消息的收件人不能为空
            return ResultData.fail(ContextUtil.getMessage("00023"));
        }
        // 调用基础服务，获取用户的消息通知信息
        ResultData<List<UserNotifyInfo>> userInfoResult = basicIntegration.findNotifyInfoByUserIds(new ArrayList<>(userIds));
        if (userInfoResult.failed()) {
            // 记录异常日志
            LogUtil.error(userInfoResult.getMessage(), new ServiceException("调用基础服务，获取用户的消息通知信息异常！"));
            return ResultData.fail(ContextUtil.getMessage("00025"));
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
            return ResultData.fail(ContextUtil.getMessage("00024"));
        }
        // 构造统一发送的消息
        SendMessage sendMessage = new SendMessage();
        sendMessage.setSubject(subject);
        sendMessage.setContent(content);
        sendMessage.setSender(sender);
        sendMessage.setReceivers(receivers);
        sendMessage.setDocIds(message.getDocIds());

        // 去重
        Set<NotifyType> notifyTypeSet = new HashSet<>(message.getNotifyTypes());
        // 循环通知方式，循环发送
        for (NotifyType notifyType : notifyTypeSet) {
            //微信/小程序单独处理模板
            if (NotifyType.WeChat.equals(notifyType) || NotifyType.MiniApp.equals(notifyType)) {
                message.setContentTemplateCode(message.getContentTemplateCode() + "_" + EnumUtils.getEnumItemName(NotifyType.class, notifyType.ordinal()).toUpperCase());
                contentBuilder.build(message);
                sendMessage.setContent(message.getContent());
            } else {
                sendMessage.setContent(content);
            }
            String sendJson = JsonUtils.toJson(sendMessage);
            mqProducer.send(notifyType.name(), sendJson);
        }
        return ResultData.success("ok");
    }

    /**
     * 发送一封电子邮件
     *
     * @param emailMessage 电子邮件消息
     */
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
            List<EmailAccount> ccAccounts = emailMessage.getCcList();
            List<UserNotifyInfo> ccList = new ArrayList<>();
            if (ccAccounts != null && ccAccounts.size() > 0) {
                UserNotifyInfo info;
                for (EmailAccount account : ccAccounts) {
                    info = UserNotifyInfo.builder()
                            .setUserName(account.getName())
                            .setEmail(account.getAddress());
                    ccList.add(info);
                }
            }

            SendMessage sendMessage = SendMessage.builder()
                    .setContent(emailMessage.getContent())
                    .setSubject(emailMessage.getSubject())
                    .setReceivers(receivers)
                    .setCcList(ccList)
                    .setDocIds(emailMessage.getDocIds());
            if (Objects.nonNull(sender)) {
                sendMessage.setSender(UserNotifyInfo.builder().setUserName(sender.getName()).setEmail(sender.getAddress()));
            }

            // JSON序列化
            String message = JsonUtils.toJson(sendMessage);
            mqProducer.send(NotifyType.EMAIL.name(), message);
        }
        return ResultData.success("OK");
    }

    /**
     * 发送平台短信通知
     *
     * @param message 短信通知
     */
    public ResultData<String> sendSms(@Valid SmsMessage message) {
        //检查消息通知方式
        if (message == null) {
            // 发送的消息类型以及内容不能为空
            return ResultData.fail(ContextUtil.getMessage("00022"));
        }
        //检查收件人是否存在
        List<String> phoneNums = message.getPhoneNums();
        if (CollectionUtils.isEmpty(phoneNums)) {
            // 发送消息的收件人不能为空
            return ResultData.fail(ContextUtil.getMessage("00023"));
        }
        Set<String> numSet = new HashSet<>(phoneNums);
        if (CollectionUtils.isEmpty(numSet)) {
            // 发送消息的收件人不能为空
            return ResultData.fail(ContextUtil.getMessage("00023"));
        }

        UserNotifyInfo info;
        List<UserNotifyInfo> receivers = new ArrayList<>();
        for (String num : numSet) {
            info = new UserNotifyInfo();
            info.setMobile(num);
            receivers.add(info);
        }
        // 生成消息
        contentBuilder.build(message);
        //消息内容
        String content = message.getContent();
        // 构造统一发送的消息
        SendMessage sendMessage = new SendMessage();
        sendMessage.setContent(content);
        sendMessage.setReceivers(receivers);

        // 循环通知方式，循环发送
        String sendJson = JsonUtils.toJson(sendMessage);
        mqProducer.send(NotifyType.SMS.name(), sendJson);
        return ResultData.success("ok");
    }

    /**
     * 给指定一个人发送系统提醒
     * 因要实时返回消息id,故采用直接发送
     *
     * @param message 系统提醒消息.消息类型为[SEI_REMIND]可不用再指定
     * @return 成功返回messageId
     */
    public ResultData<String> sendRemind(NotifyMessage message) {
        //检查收件人是否存在
        List<String> receiverIds = message.getReceiverIds();
        if (CollectionUtils.isEmpty(receiverIds)) {
            // 发送消息的收件人不能为空
            return ResultData.fail(ContextUtil.getMessage("00023"));
        }
        Set<String> userIds = new HashSet<>(receiverIds);
        if (StringUtils.isNotBlank(message.getSenderId())) {
            userIds.add(message.getSenderId());
        }
        if (CollectionUtils.isEmpty(userIds)) {
            // 发送消息的收件人不能为空
            return ResultData.fail(ContextUtil.getMessage("00023"));
        }
        // 调用基础服务，获取用户的消息通知信息
        ResultData<List<UserNotifyInfo>> userInfoResult = basicIntegration.findNotifyInfoByUserIds(new ArrayList<>(userIds));
        if (userInfoResult.failed()) {
            // 记录异常日志
            LogUtil.error(userInfoResult.getMessage(), new ServiceException("调用基础服务，获取用户的消息通知信息异常！"));
            return ResultData.fail(ContextUtil.getMessage("00025"));
        }
        List<UserNotifyInfo> userInfos = userInfoResult.getData();
        // 生成消息
        contentBuilder.build(message);

        UserNotifyInfo sender = null;
        UserNotifyInfo receiver = null;
        for (UserNotifyInfo info : userInfos) {
            if (StringUtils.isNotBlank(message.getSenderId()) && Objects.equals(info.getUserId(), message.getSenderId())) {
                sender = info;
            } else {
                receiver = info;
            }
        }
        if (Objects.isNull(receiver)) {
            // 发送消息的收件人不存在
            return ResultData.fail(ContextUtil.getMessage("00024"));
        }

        // 生成消息
        contentBuilder.build(message);
        //消息内容
        String content = message.getContent();

        Message msg = new Message();
        msg.setCategory(NotifyType.SEI_REMIND);
        //消息主题
        msg.setSubject(message.getSubject());
        msg.setTargetType(TargetType.PERSONAL);
        msg.setTargetValue(receiver.getUserId());
        msg.setTargetName(receiver.getUserName());

        if (Objects.nonNull(sender)) {
            msg.setPublishUserAccount(sender.getUserAccount());
            msg.setPublishUserName(sender.getUserName());
        }

        Set<String> docIds = message.getDocIds();
        ResultData<String> resultData = messageService.sendMessage(content, docIds, msg);

        MessageHistory history = new MessageHistory();
        history.setCategory(NotifyType.SEI_REMIND);
        history.setSubject(message.getSubject());
        history.setTargetType(TargetType.PERSONAL);
        history.setTargetValue(receiver.getUserId());
        history.setTargetName(receiver.getUserName());
        try {
            historyService.recordHistory(Lists.newArrayList(history), content, resultData.successful(), resultData.getMessage(), docIds);
        } catch (Exception e) {
            LogUtil.error("记录消息历史异常", e);
        }

        if (resultData.successful()) {
            return ResultData.success(msg.getId());
        } else {
            return resultData;
        }
    }
}
