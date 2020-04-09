package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能: Hello Test
 *
 */
public class HelloControllerTest extends BaseUnitTest {
    @Autowired
    private HelloController controller;

    @Test
    public void sayHello() {
        String name = "王锦光";
        ResultData result = controller.sayHello(name);
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void mqSayHello() throws Exception{
        String name = "wangjg";
        controller.mqSayHello(name);
        Thread.sleep(30*1000);
    }

    @Test
    public void testKey() {
        String key = HelloController.getTestKey();
        System.out.println(key);
    }
}