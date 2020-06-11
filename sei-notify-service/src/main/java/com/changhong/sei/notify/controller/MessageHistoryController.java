package com.changhong.sei.notify.controller;

import com.changhong.sei.notify.api.MessageHistoryApi;
import com.changhong.sei.notify.dto.MessageHistoryDto;
import com.changhong.sei.notify.entity.MessageHistory;
import com.changhong.sei.notify.service.MessageHistoryService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

/**
 * 消息历史(MessageHistory)控制类
 *
 * @author sei
 * @since 2020-06-11 14:36:17
 */
@RestController
@Api(value = "MessageHistoryApi", tags = "消息历史服务")
@RequestMapping(path = "messageHistory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MessageHistoryController extends BaseEntityController<MessageHistory, MessageHistoryDto> implements MessageHistoryApi {
    /**
     * 消息历史服务对象
     */
    @Autowired
    private MessageHistoryService service;

    @Override
    public BaseEntityService<MessageHistory> getService() {
        return service;
    }

}