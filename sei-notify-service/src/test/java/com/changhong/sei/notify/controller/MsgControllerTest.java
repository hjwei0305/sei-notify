package com.changhong.sei.notify.controller;

import com.changhong.com.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
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
}