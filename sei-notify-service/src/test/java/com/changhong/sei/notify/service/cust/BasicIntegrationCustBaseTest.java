package com.changhong.sei.notify.service.cust;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.AccountResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-25 08:19
 */
public class BasicIntegrationCustBaseTest extends BaseUnitTest {

    @Autowired
    private BasicIntegration integration;

    @Test
    public void findByPage() {
        Search search = Search.createSearch();

        ResultData<PageResult<AccountResponse>> resultData = integration.findAccountByPage(search);
        System.out.println(resultData);
    }

    @Test
    public void getEmployeeOrgCodes() {
        String userId = "B54E8964-D14D-11E8-A64B-0242C0A8441B";
        ResultData resultData = integration.getEmployeeOrgCodes(userId);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getEmployeePositionCodes() {
    }

    @Test
    public void getUserAuthorizedTreeEntities() {
    }

    @Test
    public void findNotifyInfoByUserIds() {
        List<String> userIds = new ArrayList<>();
        userIds.add("1F30A429-CDBB-11E8-B852-0242C0A8441B");
        userIds.add("B54E8964-D14D-11E8-A64B-0242C0A8441B");
        ResultData resultData = integration.findNotifyInfoByUserIds(userIds);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}