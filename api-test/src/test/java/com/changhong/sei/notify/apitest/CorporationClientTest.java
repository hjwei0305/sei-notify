package com.changhong.sei.notify.apitest;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.apitest.basic.CorporationClient;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能: BASIC公司API测试
 *
 * @author 王锦光 wangjg
 * @version 2020-01-31 21:21
 */
public class CorporationClientTest extends BaseUnitTest {
    @Autowired
    private CorporationClient client;

    @Test
    public void findByCode() {
        String code = "10044-Q000";
        ResultData resultData = client.findByCode(code);
        Assert.assertTrue(resultData.successful());
        System.out.println(JsonUtils.toJson(resultData));
    }
}
