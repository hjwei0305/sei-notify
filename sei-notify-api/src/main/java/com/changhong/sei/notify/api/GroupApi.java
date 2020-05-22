package com.changhong.sei.notify.api;

import com.changhong.sei.notify.dto.GroupDto;
import com.changhong.sei.core.api.BaseEntityApi;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 群组(Group)API
 *
 * @author sei
 * @since 2020-05-22 11:04:28
 * TODO @FeignClient(name = "请修改为项目服务名")
 */
@Valid
@FeignClient(name = "sei-notify", path = "group")
public interface GroupApi extends BaseEntityApi<GroupDto> {

}