package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.ContentParams;
import com.changhong.sei.notify.dto.ContentTemplateDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 16:23
 */
public class ContentTemplateControllerTest extends BaseUnitTest {
    @Autowired
    private ContentTemplateController controller;

    @Test
    public void save() {
        ContentTemplateDto dto = new ContentTemplateDto();
        dto.setCode("test-001");
        dto.setName("测试模板-001");
        //dto.setContent("测试模板-001的内容");
        ResultData<ContentTemplateDto> resultData = controller.save(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void findOne() {
        String id = "c0a80a70-6f31-1c8f-816f-31ecab230000";
        ResultData resultData = controller.findOne(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void delete() {
        String id = "0a630227-6f65-152a-816f-6506656e0000";
        ResultData resultData = controller.delete(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void findByCode() {
        String code = "test";
        ResultData resultData = controller.findByCode(code);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void checkDto(){
        ContentTemplateDto dto = new ContentTemplateDto();
        // dto.setCode("test-001");
        dto.setName("测试模板-001");
        //dto.setContent("测试模板-001的内容");
        ResultData resultData = controller.checkDto(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void findAll(){
        ResultData resultData = controller.findAll();
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getContent() {
        ContentParams contentParams = new ContentParams();
        contentParams.setTemplateCode("EMAIL_TEMPLATE_REGIST");
        Map<String,Object> params = new HashMap<>();
        params.put("userName","程序员");
        params.put("account","devuser");
        params.put("password","123456");
        contentParams.setParams(params);
        ResultData resultData = controller.getContent(contentParams);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}