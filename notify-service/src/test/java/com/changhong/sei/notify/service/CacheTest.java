package com.changhong.sei.notify.service;

import com.changhong.sei.notify.BaseUnitTest;
import com.changhong.sei.notify.entity.ContentTemplate;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:缓存测试
 * @date 2019/12/30 15:11
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING )
public class CacheTest extends BaseUnitTest {

//    @Autowired
//    private CacheUtil<String, ContentTemplate> cacheUtils;
//
//    @Test
//    public void test_01_Put(){
//        ContentTemplate contentTemplate = new ContentTemplate();
//        contentTemplate.setCode("test-001");
//        contentTemplate.setName("测试模板-001");
//        contentTemplate.setContent("测试模板-001的内容");
//        cacheUtils.set(contentTemplate.getCode(),contentTemplate);
//    }
//
//    @Test
//    public void test_02_Get(){
//        ContentTemplate contentTemplate = new ContentTemplate();
//        contentTemplate.setCode("test-001");
//        contentTemplate.setName("测试模板-001");
//        contentTemplate.setContent("测试模板-001的内容");
//        ContentTemplate cache = cacheUtils.get(contentTemplate.getCode());
//        Assert.assertEquals(contentTemplate.getName(),cache.getName());
//    }
//
//    @Test
//    public void test_03_Del(){
//        ContentTemplate contentTemplate = new ContentTemplate();
//        contentTemplate.setCode("test-001");
//        contentTemplate.setName("测试模板-001");
//        contentTemplate.setContent("测试模板-001的内容");
//        cacheUtils.delete(contentTemplate.getCode());
//    }
//
//    @Test
//    public void test_04_expire(){
//        ContentTemplate contentTemplate = new ContentTemplate();
//        contentTemplate.setCode("test-001");
//        contentTemplate.setName("测试模板-001");
//        contentTemplate.setContent("测试模板-001的内容");
//        cacheUtils.set(contentTemplate.getCode(),contentTemplate,200);
//    }
}
