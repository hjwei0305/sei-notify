package com.changhong.sei.notify.sdk.client;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.EmailMessage;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.SmsMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * <strong>实现功能:</strong>
 * <p>平台消息通知服务API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-13 16:31
 */
@FeignClient(name = "sei-notify", path = "notify")
public interface NotifyClient {

    /**
     * 发送平台消息通知
     *
     * @param message 平台消息
     */
    @PostMapping(path = "send", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultData<String> send(@RequestBody @Valid NotifyMessage message);

    /**
     * 发送平台短信通知
     *
     * @param message 短信通知
     */
    @PostMapping(path = "sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultData<String> sendSms(@RequestBody @Valid SmsMessage message);

    /**
     * 发送一封电子邮件
     *
     * @param emailMessage 电子邮件消息
     */
    @PostMapping(path = "sendEmail", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultData<String> sendEmail(@RequestBody EmailMessage emailMessage);

    /**
     * 给指定一个人发送系统提醒
     *
     * @param message 系统提醒消息.消息类型为[SEI_REMIND]可不用再指定
     * @return 成功返回messageId
     */
    @PostMapping(path = "sendRemind", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultData<String> sendRemind(@RequestBody NotifyMessage message);

    /**
     * 根据群组获取用户id集合
     *
     * @param groupCode 群组代码
     * @return 用户id集合
     */
    @GetMapping(path = "getUserIdsByGroup", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultData<List<String>> getUserIdsByGroup(@RequestParam("groupCode") String groupCode);
}
