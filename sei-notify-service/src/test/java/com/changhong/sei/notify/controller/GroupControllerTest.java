package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.GroupDto;
import com.changhong.sei.notify.dto.GroupUserDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Test
    public void addUserAccounts() {
        String json = "[{\"userAccount\":\"sei\",\"userId\":\"8f9f3a92-3f82-11e7-ac6f-005056930c6b\",\"groupId\":\"C9399501-9BF8-11EA-9F72-0242C0A8460B\",\"userName\":\"全局管理员\"}]";
        List<GroupUserDto> groupUserDtos = JsonUtils.fromJson2List(json, GroupUserDto.class);
        ResultData<String> resultData = controller.addGroupUser(groupUserDtos);
        LOG.debug(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getUserAccounts() {
//        Search search = Search.createSearch();
        String json = "{\"quickSearchValue\":\"admin\",\"quickSearchProperties\":[\"userName\",\"userAccount\"],\"pageInfo\":{\"page\":1,\"rows\":15}}";
        Search search = JsonUtils.fromJson(json, Search.class);
//        search.setQuickSearchValue("admin");
        ResultData<PageResult<GroupUserDto>> resultData = controller.getUserAccounts(search);
        LOG.debug(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

}