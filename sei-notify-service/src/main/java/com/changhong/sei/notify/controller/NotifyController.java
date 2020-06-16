package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.api.NotifyApi;
import com.changhong.sei.notify.dto.EmailMessage;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.SmsMessage;
import com.changhong.sei.notify.service.NotifyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
   private NotifyService notifyService;


    /**
     * 发送平台消息通知
     *
     * @param message 平台消息
     */
    @Override
    public ResultData<String> send(@Valid NotifyMessage message) {
        return notifyService.send(message);
    }

    /**
     * 发送平台短信通知
     *
     * @param message 短信通知
     */
    @Override
    public ResultData<String> sendSms(@Valid SmsMessage message) {
        return notifyService.sendSms(message);
    }

    /**
     * 发送一封电子邮件
     *
     * @param emailMessage 电子邮件消息
     */
    @Override
    public ResultData<String> sendEmail(EmailMessage emailMessage) {
        return notifyService.sendEmail(emailMessage);
    }
}
