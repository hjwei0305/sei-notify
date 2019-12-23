package com.changhong.sei.notify.dao;

import com.changhong.sei.notify.BaseUnitTest;
import com.changhong.sei.notify.entity.ContentTemplate;
import com.chonghong.sei.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 11:18
 */
public class ContentTemplateDaoTest  extends BaseUnitTest {
    @Autowired
    private ContentTemplateDao dao;

    @Test
    public void findByCode(){
        String code = "test";
        ContentTemplate template = dao.findByCode(code);
        Assert.assertNotNull(template);
        System.out.println(JsonUtils.toJson(template));
    }
}