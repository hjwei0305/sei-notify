package com.changhong.sei.notify.service.client;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-06 14:47
 */
public class UserNotifyInfoClientTest extends BaseUnitTest {
    @Autowired
    private UserNotifyInfoClient client;

    @Test
    public void findNotifyInfoByUserIds(){
        List<String> userIds = new ArrayList<>();
        userIds.add("1F30A429-CDBB-11E8-B852-0242C0A8441B");
        userIds.add("B54E8964-D14D-11E8-A64B-0242C0A8441B");
        ResultData resultData = client.findNotifyInfoByUserIds(userIds);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}