package com.changhong.sei.notify.manager.client;

import com.changhong.sei.core.dto.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <strong>实现功能:</strong>
 * <p>用户消息通知信息服务API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-13 17:22
 */
@FeignClient(name = "sei-basic", path = "userProfile")
public interface UserNotifyInfoClient {
    /**
     * 通过用户Id清单获取用户信息
     * @param userIds 用户Id清单
     * @return 用户信息
     */
    @PostMapping(path = "findNotifyInfoByUserIds", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultData<List<UserNotifyInfo>> findNotifyInfoByUserIds(@RequestBody List<String> userIds);
}
