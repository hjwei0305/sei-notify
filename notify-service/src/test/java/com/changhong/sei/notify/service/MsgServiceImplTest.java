package com.changhong.sei.notify.service;

import com.changhong.com.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-16 13:34
 */
public class MsgServiceImplTest extends BaseUnitTest {
    @Autowired
    private MsgServiceImpl service;

    @Test
    public void getPriority() {
        ResultData result = service.getPriority();
        System.out.println(JsonUtils.toJson(result));
        Assert.assertTrue(result.isSuccessful());
    }
}