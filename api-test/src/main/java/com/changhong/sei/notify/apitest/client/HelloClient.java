package com.changhong.sei.notify.apitest.client;

import com.changhong.sei.notify.api.HelloService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <strong>实现功能:</strong>
 * <p>Hello的Feign客户端</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-14 13:43
 */
@FeignClient(name = "sei-notify", path = "hello")
public interface HelloClient extends HelloService {
}
