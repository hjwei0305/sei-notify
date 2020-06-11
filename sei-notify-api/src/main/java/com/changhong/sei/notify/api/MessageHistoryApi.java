package com.changhong.sei.notify.api;

import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.MessageHistoryDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * 消息历史(MessageHistory)API
 *
 * @author sei
 * @since 2020-06-11 14:36:34
 */
@Valid
@FeignClient(name = "sei-notify", path = "messageHistory")
public interface MessageHistoryApi extends FindByPageApi<MessageHistoryDto> {

    /**
     * 通过Id获取一个消息历史
     *
     * @param id 消息历史Id
     * @return 消息历史
     */
    @GetMapping(path = "findOne")
    @ApiOperation(value = "获取一个消息历史", notes = "通过Id获取一个消息历史")
    ResultData<MessageHistoryDto> findOne(@RequestParam("id") String id);
}