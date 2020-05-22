package com.changhong.sei.notify.controller;

import com.changhong.sei.notify.api.GroupApi;
import com.changhong.sei.notify.dto.GroupDto;
import com.changhong.sei.notify.entity.Group;
import com.changhong.sei.notify.service.GroupService;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

/**
 * 群组(Group)控制类
 *
 * @author sei
 * @since 2020-05-22 11:04:12
 */
@RestController
@Api(value = "GroupApi", tags = "群组服务")
@RequestMapping(path = "group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GroupController extends BaseEntityController<Group, GroupDto> implements GroupApi {
    /**
     * 群组服务对象
     */
    @Autowired
    private GroupService service;

    @Override
    public BaseEntityService<Group> getService() {
        return service;
    }

}