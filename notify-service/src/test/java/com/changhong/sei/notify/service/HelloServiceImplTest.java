package com.changhong.sei.notify.service;

import com.changhong.sei.notify.BaseUnitTest;
import com.changhong.sei.notify.dto.ResultData;
import com.chonghong.sei.util.JsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-18 11:21
 */
public class HelloServiceImplTest extends BaseUnitTest {
    @Autowired
    private HelloServiceImpl service;

    @Test
    public void sayHello() {
        String name = "王锦光";
        ResultData result = service.sayHello(name);
        System.out.println(JsonUtils.toJson(result));
    }
}