package com.changhong.sei.notify.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.RemindRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 提醒信息(Remind)API
 *
 * @author sei
 * @since 2020-05-22 18:09:43
 */
@Valid
@FeignClient(name = "sei-notify", path = "remind")
public interface RemindApi {

    /**
     * 发送提醒
     *
     * @param remindRequest 发起提醒请求
     * @return 发送结果
     */
    ResultData<String> sendRemind(@RequestBody @Valid RemindRequest remindRequest);
}