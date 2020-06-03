package com.changhong.sei.notify.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.ContentParams;
import com.changhong.sei.notify.dto.ContentTemplateDto;
import com.changhong.sei.notify.dto.UserMiniAppPushTimeDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * <strong>实现功能:</strong>
 * <p>内容模板API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 15:41
 */
@FeignClient(name = "sei-notify", path = "userMiniAppPushTime")
public interface UserMiniAppPushTimeApi extends BaseEntityApi<UserMiniAppPushTimeDto>{

    /**
     * 推送次数加一
     * @param openId 小程序openId
     * @return 结果
     */
    @ApiOperation("推送次数加一")
    @GetMapping(path = "plus")
    ResultData<String> plus(@RequestParam("openId") String openId);

    /**
     * 推送次数减一
     * @param openId 小程序openId
     * @return 结果
     */
    @ApiOperation("推送次数减一")
    @GetMapping(path = "subtract")
    ResultData<String> subtract(@RequestParam("openId") String openId);
}
