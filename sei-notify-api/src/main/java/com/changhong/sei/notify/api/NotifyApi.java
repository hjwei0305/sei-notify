package com.changhong.sei.notify.api;

import com.changhong.sei.notify.dto.NotifyMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <strong>实现功能:</strong>
 * <p>平台消息通知服务API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-13 16:31
 */
@FeignClient(name = "sei-notify", path = "notify")
@RequestMapping(path = "notify", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface NotifyApi {

    /**
     * 发送平台消息通知
     * @param message 平台消息
     */
    @ApiOperation(value = "发送平台消息通知", notes = "发送一个平台消息到消息服务队列")
    @PostMapping(path = "send", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void send(@RequestBody NotifyMessage message);
}
