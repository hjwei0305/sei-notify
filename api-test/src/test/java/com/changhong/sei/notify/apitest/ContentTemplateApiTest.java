package com.changhong.sei.notify.apitest;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.api.ContentTemplateApi;
import com.changhong.sei.notify.dto.ContentTemplateDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-14 9:25
 */
public class ContentTemplateApiTest extends BaseUnitTest {
    @Autowired
    private ContentTemplateApi contentTemplateApi;

    @Test
    public void findByCode(){
        String code = "EMAIL_TEMPLATE_REGIST";
        ResultData<ContentTemplateDto> resultData = contentTemplateApi.findByCode(code);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void findOne(){
        String id = "27B77612-3090-11EA-A16E-080058000005";
        ResultData<ContentTemplateDto> resultData = contentTemplateApi.findOne(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void save() {
        ContentTemplateDto dto = new ContentTemplateDto();
        dto.setCode("test-001");
        dto.setName("测试模板-001dddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        dto.setContent("测试模板-001的内容");
        ResultData resultData = contentTemplateApi.save(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void delete(){
        String id = "B0141961-38D8-11EA-9FF3-0242C0A84604";
        ResultData resultData = contentTemplateApi.delete(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void findAll(){
        ResultData<List<ContentTemplateDto>> resultData = contentTemplateApi.findAll();
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}
