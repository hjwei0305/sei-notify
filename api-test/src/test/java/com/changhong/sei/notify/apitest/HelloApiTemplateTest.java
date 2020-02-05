package com.changhong.sei.notify.apitest;

import com.changhong.com.sei.core.test.BaseUnitTest;
import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <strong>实现功能:</strong>
 * <p>Feign测试API模板调用</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-12 20:00
 */
public class HelloApiTemplateTest extends BaseUnitTest {
    @Autowired
    private ApiTemplate apiTemplate;

    @Test
    public void sayHello(){
        String uri = "http://10.4.208.86:20001/sei-notify/hello/sayHello";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("name", "wangjg");
        ResultData result = apiTemplate.getByUrl(uri, ResultData.class, params);
        System.out.println(JsonUtils.toJson(result));
        Assert.assertTrue(result.successful());
    }

    @Test
    public void sayHelloByAppName(){
        String appname = "sei-notify";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("name", "wangjg");
        ResultData result = apiTemplate.getByAppModuleCode(appname, "hello/sayHello", ResultData.class, params);
        System.out.println(JsonUtils.toJson(result));
        Assert.assertTrue(result.successful());
    }

    @Test
    public void health(){
        String appname = "sei-notify";
        String path = "actuator/health";
        String result = apiTemplate.getByAppModuleCode(appname, path, String.class);
        System.out.println(JsonUtils.toJson(result));
    }
}
