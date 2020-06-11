package com.changhong.sei.notify.api;

import com.changhong.sei.notify.dto.MessageHistoryDto;
import com.changhong.sei.core.api.BaseEntityApi;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 消息历史(MessageHistory)API
 *
 * @author sei
 * @since 2020-06-11 14:36:34
 */
@Valid
@FeignClient(name = "sei-notify", path = "messageHistory")
public interface MessageHistoryApi extends BaseEntityApi<MessageHistoryDto> {

}