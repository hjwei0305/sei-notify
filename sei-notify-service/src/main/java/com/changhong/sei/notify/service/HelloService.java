package com.changhong.sei.notify.service;

import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.mq.MqProducer;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.SendMessage;
import com.changhong.sei.notify.manager.demo.HelloManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <strong>实现功能:</strong>
 * <p>调试你好的业务逻辑实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-18 19:42
 */
@Service
@CacheConfig(cacheNames = "hello_cache")
public class HelloService {
    /**
     * 注入消息队列生产者
     */
    @Autowired
    private MqProducer mqProducer;

    /**
     * 你好业务逻辑
     *
     * @param name  姓名
     * @param param 参数
     * @return 返回句子
     */
    @Cacheable(key = "#name+'_'+#param")
    public String sayHello(String name, String param) {
        LogUtil.bizLog("执行业务逻辑说：你好！");
        return "你好，" + name + "！参数：" + param;
    }

    /**
     * 通过消息队列说你好
     *
     * @param name  姓名
     * @param param 参数
     */
    @Transactional(rollbackFor = Exception.class)
    public void mqSayHello(String name, String param) {
        LogUtil.bizLog("通过消息队列说：你好！");
        SendMessage sendMessage = SendMessage.builder().setContent("你好，" + name + "！参数：" + param);
        mqProducer.send(HelloManager.HELLO_MQ_KEY, JsonUtils.toJson(sendMessage));
    }
}
