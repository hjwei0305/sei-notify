package com.changhong.sei.notify.api;

import com.changhong.sei.notify.dto.EmailMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <strong>实现功能:</strong>
 * <p>发送电子邮件的API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-13 11:03
 */
@RestController
@RequestMapping(path = "emailNotify", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface EmailNotifyService {
    /**
     * 发送一封电子邮件
     * @param emailMessage 电子邮件消息
     */
    @ApiOperation(value = "发送一封电子邮件", notes = "发送一封邮件信息到邮件服务队列")
    @PostMapping(path = "sendEmail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void sendEmail(@RequestBody EmailMessage emailMessage);
}
