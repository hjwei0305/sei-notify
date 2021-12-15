package com.changhong.sei.notify.sdk.client;

import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-12-15 17:02
 */
@FeignClient(name = "sei-basic")
public interface BasicClient {
    /**
     * 根据岗位的code获取已分配的员工Id
     *
     * @param orgCode      组织code
     * @param positionCode 岗位code
     * @return userId列表
     */
    @GetMapping(path = "position/getUserIdsByOrgCodePositionCode")
    ResultData<List<String>> getUserIdsByOrgCodePositionCode(@RequestParam("orgCode") String orgCode, @RequestParam("positionCode") String positionCode);

    /**
     * 根据功能角色的code获取已分配的用户id
     *
     * @param featureRoleCode 功能角色的code
     * @return 用户id清单
     */
    @GetMapping(path = "featureRole/getUserIdsByFeatureRole")
    ResultData<List<String>> getUserIdsByFeatureRole(@RequestParam("featureRoleCode") String featureRoleCode);

}
