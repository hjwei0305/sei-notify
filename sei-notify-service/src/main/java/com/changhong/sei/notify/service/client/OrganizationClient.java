package com.changhong.sei.notify.service.client;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.OrganizationDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <strong>实现功能:</strong>
 * <p>组织服务的API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-16 14:29
 */
@FeignClient(name = "sei-basic", path = "organization")
//@FeignClient(name = "${sei.basic.server-name}", url = "${sei.basic.server-url}", path = "organization")
public interface OrganizationClient {
    /**
     * 获取当前用户有权限的树形组织实体清单
     *
     * @param featureCode 功能项代码
     * @return 有权限的树形组织实体清单
     */
    @GetMapping(path = "getUserAuthorizedTreeEntities")
    @ApiOperation(value = "获取当前用户有权限的树形组织实体清单", notes = "获取当前用户有权限的树形组织实体清单")
    ResultData<List<OrganizationDto>> getUserAuthorizedTreeEntities(@RequestParam(value = "featureCode", required = false, defaultValue = "") String featureCode);
}
