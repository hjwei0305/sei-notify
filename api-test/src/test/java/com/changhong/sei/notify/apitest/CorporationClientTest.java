package com.changhong.sei.notify.apitest;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.apitest.basic.CorporationClient;
import com.changhong.sei.notify.apitest.basic.CorporationDto;
import com.changhong.sei.utils.AsyncRunUtil;
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

    @Autowired
    private AsyncRunUtil runAsync;

    @Test
    public void findByCode() {
        String code = "10044-Q000";
        ResultData resultData = client.findByCode(code);
        Assert.assertTrue(resultData.successful());
        System.out.println(JsonUtils.toJson(resultData));
    }

    @Test
    public void saveAsync() throws InterruptedException {
        CorporationDto dto = new CorporationDto();
        dto.setCode("10044-test01");
        dto.setName("测试公司");
        dto.setShortName("测试");
        dto.setErpCode("TEST");
        dto.setBaseCurrencyCode("RMB");
        dto.setBaseCurrencyName("人民币");
        runAsync.runAsync(() -> {
            ResultData resultData = client.save(dto);
            System.out.println(JsonUtils.toJson(resultData));
        });
        System.out.println("异步方法执行结束！");
        Thread.sleep(30*1000);
    }
}
