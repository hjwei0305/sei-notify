package com.changhong.sei.notify.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <strong>实现功能:</strong>
 * <p>调试你好的业务逻辑实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-16 17:22
 */
@Service
public class HelloService {

    @Value("${notify.test-key}")
    private String testKey;
    /**
     * 你好
     * @param name 姓名
     * @return 返回句子
     */
    public String sayHello(String name){
        return "你好，"+name+"！全局参数："+testKey;
    }
}
