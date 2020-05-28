package com.changhong.sei.notify.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.EmailMessage;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.SmsMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * <strong>实现功能:</strong>
 * <p>平台消息通知服务API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-13 16:31
 */
@FeignClient(name = "sei-notify", path = "notify")
public interface NotifyApi {

    /**
     * 发送平台消息通知
     * @param message 平台消息
     */
    @ApiOperation(value = "发送平台消息通知", notes = "发送一个平台消息到消息服务队列")
    @PostMapping(path = "send", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultData<String> send(@RequestBody @Valid NotifyMessage message);

    /**
     * 发送平台短信通知
     * @param message 短信通知
     */
    @ApiOperation(value = "发送平台短信通知", notes = "发送一个平台消息到消息服务队列")
    @PostMapping(path = "sendSms", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultData<String> sendSms(@RequestBody @Valid SmsMessage message);

    /**
     * 发送一封电子邮件
     * @param emailMessage 电子邮件消息
     */
    @ApiOperation(value = "发送一封电子邮件", notes = "发送一封邮件信息到邮件服务队列")
    @PostMapping(path = "sendEmail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultData<String> sendEmail(@RequestBody EmailMessage emailMessage);
}
