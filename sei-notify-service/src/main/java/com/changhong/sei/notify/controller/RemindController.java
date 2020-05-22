package com.changhong.sei.notify.controller;

import com.changhong.sei.notify.api.RemindApi;
import com.changhong.sei.notify.dto.RemindDto;
import com.changhong.sei.notify.entity.Remind;
import com.changhong.sei.notify.service.RemindService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

/**
 * 提醒信息(Remind)控制类
 *
 * @author sei
 * @since 2020-05-22 18:09:16
 */
@RestController
@Api(value = "RemindApi", tags = "提醒信息服务")
@RequestMapping(path = "remind", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RemindController extends BaseEntityController<Remind, RemindDto> implements RemindApi {
    /**
     * 提醒信息服务对象
     */
    @Autowired
    private RemindService service;

    @Override
    public BaseEntityService<Remind> getService() {
        return service;
    }

}