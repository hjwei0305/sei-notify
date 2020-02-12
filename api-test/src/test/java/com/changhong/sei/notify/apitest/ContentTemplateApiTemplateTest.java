package com.changhong.sei.notify.apitest;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-01-22 20:24
 */
public class ContentTemplateApiTemplateTest extends BaseUnitTest {
    @Autowired
    private ApiTemplate apiTemplate;

    @Test
    public void findAll(){
        String appname = "sei-notify";
        ResultData result = apiTemplate.getByAppModuleCode(appname, "contentTemplate/findAll", ResultData.class);
        System.out.println(JsonUtils.toJson(result));
        Assert.assertTrue(result.successful());
    }
}
