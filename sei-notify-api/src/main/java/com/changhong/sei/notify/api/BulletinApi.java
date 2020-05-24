package com.changhong.sei.notify.api;

import com.changhong.sei.core.api.FindByPageApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.BulletinDto;
import com.changhong.sei.notify.dto.OrganizationDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * <strong>实现功能:</strong>
 * <p>维护消息通告API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-15 14:56
 */
@FeignClient(name = "sei-notify", path = "bulletin")
public interface BulletinApi extends FindByPageApi<BulletinDto> {
    /**
     * 保存消息通告
     * @param bulletinDto 消息通告DTO
     * @return 操作结果
     */
    @PostMapping(path = "saveBulletin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "保存通告", notes = "维护一个消息通告")
    ResultData<String> saveBulletin(@RequestBody BulletinDto bulletinDto);

    /**
     * 发布通告
     * @param ids 通告Id清单
     * @return 业务处理结果
     */
    @PostMapping(path = "releaseBulletin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "发布通告", notes = "批量发布通告")
    ResultData<String> releaseBulletin(@RequestBody Set<String> ids);

    /**
     * 撤销通告
     * @param ids 通告Id清单
     * @return 业务处理结果
     */
    @PostMapping(path = "cancelBulletin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "撤销通告", notes = "批量撤销通告")
    ResultData<String> cancelBulletin(@RequestBody Set<String> ids);

    /**
     * 删除通告
     * @param ids 通告Id清单
     * @return 业务处理结果
     */
    @PostMapping(path = "deleteBulletin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "删除通告", notes = "批量删除通告")
    ResultData<String> deleteBulletin(@RequestBody Set<String> ids);

    /**
     * 查看通告
     * @param id 通告Id
     * @return 获取一个通告
     */
    @GetMapping(path = "getBulletin")
    @ApiOperation(value = "查看通告", notes = "获取一个通告")
    ResultData<BulletinDto> getBulletin(@RequestParam("id") String id);

    /**
     * 获取当前用户有权限的树形组织实体清单
     *
     * @param featureCode 功能项代码
     * @return 有权限的树形组织实体清单
     */
    @GetMapping(path = "getUserAuthorizedTreeOrg")
    @ApiOperation(value = "获取当前用户有权限的树形组织实体清单", notes = "获取当前用户有权限的树形组织实体清单")
    ResultData<List<OrganizationDto>> getUserAuthorizedTreeOrg(@RequestParam(value = "featureCode", required = false, defaultValue = "") String featureCode);
}
