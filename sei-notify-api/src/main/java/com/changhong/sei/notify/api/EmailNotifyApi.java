package com.changhong.sei.notify.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.EmailMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <strong>实现功能:</strong>
 * <p>发送电子邮件的API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-13 11:03
 */
@FeignClient(name = "sei-notify", path = "emailNotify")
public interface EmailNotifyApi {
    /**
     * 发送一封电子邮件
     * @param emailMessage 电子邮件消息
     */
    @ApiOperation(value = "发送一封电子邮件", notes = "发送一封邮件信息到邮件服务队列")
    @PostMapping(path = "sendEmail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultData<String> sendEmail(@RequestBody EmailMessage emailMessage);
}
