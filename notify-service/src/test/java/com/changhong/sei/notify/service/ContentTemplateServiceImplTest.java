package com.changhong.sei.notify.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.BaseUnitTest;
import com.changhong.sei.notify.api.ContentTemplateService;
import com.changhong.sei.notify.dto.ContentTemplateDto;
import com.chonghong.sei.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 16:23
 */
public class ContentTemplateServiceImplTest extends BaseUnitTest {
    @Autowired
    private ContentTemplateService service;

//    @Test
//    public void findAll() {
//        ResultData<List<ContentTemplateDto>> resultData = service.findAll();
//        System.out.println(JsonUtils.toJson(resultData));
//        Assert.assertTrue(resultData.getSuccessful());
//    }

    @Test
    public void save() {
        ContentTemplateDto dto = new ContentTemplateDto();
        dto.setCode("test-001");
        dto.setName("测试模板-001");
        dto.setContent("测试模板-001的内容");
        ResultData<ContentTemplateDto> resultData = service.save(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.getSuccessful());
    }

    @Test
    public void findOne() {
        String id = "c0a80a70-6f31-1c8f-816f-31ecab230000";
        ResultData resultData = service.findOne(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.getSuccessful());
    }

    @Test
    public void delete() {
        String id = "c0a80a70-6f31-1c8f-816f-31ecab230000";
        ResultData resultData = service.delete(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.getSuccessful());
    }

    @Test
    public void findByCode() {
        String code = "test-001";
        ResultData resultData = service.findByCode(code);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.getSuccessful());
    }
}