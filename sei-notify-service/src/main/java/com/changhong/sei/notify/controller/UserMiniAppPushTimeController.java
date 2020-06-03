package com.changhong.sei.notify.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.api.HelloApi;
import com.changhong.sei.notify.api.UserMiniAppPushTimeApi;
import com.changhong.sei.notify.dto.UserMiniAppPushTimeDto;
import com.changhong.sei.notify.entity.UserMiniAppPushTime;
import com.changhong.sei.notify.service.HelloService;
import com.changhong.sei.notify.service.UserMiniAppPushTimeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <strong>实现功能:</strong>
 * <p>小程序推送次数的API服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-16 17:22
 */
@RestController
@Api(value = "UserMiniAppPushTimeApi", tags = "小程序推送次数的API服务")
@RequestMapping(path = "userMiniAppPushTime", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserMiniAppPushTimeController extends BaseEntityController<UserMiniAppPushTime, UserMiniAppPushTimeDto> implements UserMiniAppPushTimeApi {

    @Autowired
    private UserMiniAppPushTimeService service;

    @Override
    public BaseEntityService<UserMiniAppPushTime> getService() {
        return service;
    }

    /**
     * 获取剩余推送次数
     *
     * @param openId 小程序openId
     * @return 结果
     */
    @Override
    public ResultData<UserMiniAppPushTimeDto> findByOpenId(String openId) {
        ResultData<UserMiniAppPushTime> resultData = service.findByOpenId(openId);
        if (resultData.successful()){
            return ResultData.success(convertToDto(resultData.getData()));
        }else {
            return ResultData.fail(resultData.getMessage());
        }
    }

    /**
     * 推送次数加一
     *
     * @param openId 小程序openId
     * @return 结果
     */
    @Override
    public ResultData<String> plus(String openId) {
        return service.plus(openId);
    }

    /**
     * 推送次数减一
     *
     * @param openId 小程序openId
     * @return 结果
     */
    @Override
    public ResultData<String> subtract(String openId) {
        return service.subtract(openId);
    }
}
