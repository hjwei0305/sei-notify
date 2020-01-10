package com.changhong.sei.notify.feign;

import com.changhong.com.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.api.HelloService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-10 21:59
 */
public class HelloServiceTest extends BaseUnitTest {
    @Autowired
    private HelloService helloService;

    @Test
    public void sayHello() {
        String name = "王锦光";
        ResultData result = helloService.sayHello(name);
        System.out.println(JsonUtils.toJson(result));
    }
}
