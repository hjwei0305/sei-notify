package com.changhong.sei.notify.controller;

import com.changhong.sei.notify.dto.GroupDto;
import com.changhong.sei.notify.entity.Group;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 群组(Group)单元测试类
 *
 * @author sei
 * @since 2020-05-22 11:08:35
 */
public class GroupControllerTest extends BaseUnitTest {

    @Autowired
    private GroupController controller;

    @Test
    public void findOne() {
        String id = "";
        ResultData<GroupDto> resultData = controller.findOne(id);
        LOG.debug(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

}