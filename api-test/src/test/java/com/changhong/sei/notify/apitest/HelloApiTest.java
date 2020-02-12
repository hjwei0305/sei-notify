package com.changhong.sei.notify.apitest;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.api.HelloApi;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <strong>实现功能:</strong>
 * <p>Feign测试API调用</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-12 20:00
 */
public class HelloApiTest extends BaseUnitTest {
    @Autowired
    private HelloApi helloApi;

    @Test
    public void say(){
        System.out.println("Feign测试API调用:say");
    }

    @Test
    public void sayHello(){
        ResultData<String> result = helloApi.sayHello("wangjg");
        System.out.println(JsonUtils.toJson(result));
        Assert.assertTrue(result.successful());
    }
}
