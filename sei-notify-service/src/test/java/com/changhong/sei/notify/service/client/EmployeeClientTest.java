package com.changhong.sei.notify.service.client;

import com.changhong.com.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-06 15:50
 */
public class EmployeeClientTest extends BaseUnitTest {
    @Autowired
    private EmployeeClient client;

    @Test
    public void getEmployeeOrgCodes(){
        String userId = "B54E8964-D14D-11E8-A64B-0242C0A8441B";
        ResultData resultData = client.getEmployeeOrgCodes(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}