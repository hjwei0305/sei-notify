package com.changhong.sei.notify.controller;

import com.changhong.sei.core.cache.CacheBuilder;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.entity.ContentTemplate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:缓存测试
 * @date 2019/12/30 15:11
 */
public class CacheTest extends BaseUnitTest {

//    @Autowired
//    private CacheManager cacheManager;
//    private static final String CACHE_NAME="contentTemplate_cache";
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private CacheBuilder cacheBuilder;


//    /**
//     * 获取缓存操作类
//     * @return 缓存操作类
//     */
//    private Cache getCache(){
//        return cacheManager.getCache(CACHE_NAME);
//    }

    private ContentTemplate create(String code) {
        ContentTemplate contentTemplate = new ContentTemplate();
        contentTemplate.setCode(code);
        contentTemplate.setName("测试模板-001");
        contentTemplate.setContent("测试模板-001的内容");
        return contentTemplate;
    }

    @Test
    public void testPut(){
        String code = "test-001";
        cacheBuilder.set(code, create(code), 60000L);
    }

    @Test
    public void testGet() {
        String code = "test-001";
        ContentTemplate cache = cacheBuilder.get(code);
        Assert.assertNotNull(cache);
        System.out.println(JsonUtils.toJson(cache));
    }

//    @Test
//    public void testPutAndGetByRedis(){
//        String code = "test-001";
//        ContentTemplate contentTemplate = new ContentTemplate();
//        contentTemplate.setCode(code);
//        contentTemplate.setName("测试模板-001");
//        contentTemplate.setContent("测试模板-001的内容");
//        redisTemplate.opsForValue().set(code, contentTemplate);
//
//        ContentTemplate cache = (ContentTemplate)redisTemplate.opsForValue().get(code);
//        Assert.assertNotNull(cache);
//        System.out.println(JsonUtils.toJson(cache));
//    }
}
