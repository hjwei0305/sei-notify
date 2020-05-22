package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.BulletinDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-16 13:34
 */
public class MsgControllerTest extends BaseUnitTest {
    @Autowired
    private MsgController controller;

    @Test
    public void getPriority() {
        ResultData result = controller.getPriority();
        System.out.println(JsonUtils.toJson(result));
        Assert.assertTrue(result.successful());
    }

    @Test
    public void findBulletinByPage4User() {
        Search search = Search.createSearch();
        ResultData<PageResult<BulletinDto>> result = controller.findBulletinByPage4User(search);
        System.out.println(JsonUtils.toJson(result));
        Assert.assertTrue(result.successful());
    }
}