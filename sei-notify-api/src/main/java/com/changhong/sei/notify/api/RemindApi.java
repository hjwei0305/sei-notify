package com.changhong.sei.notify.api;

import com.changhong.sei.notify.dto.RemindDto;
import com.changhong.sei.core.api.BaseEntityApi;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 提醒信息(Remind)API
 *
 * @author sei
 * @since 2020-05-22 18:09:43
 */
@Valid
@FeignClient(name = "sei-notify", path = "remind")
public interface RemindApi extends BaseEntityApi<RemindDto> {

}