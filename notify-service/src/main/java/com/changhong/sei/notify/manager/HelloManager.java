package com.changhong.sei.notify.manager;

import org.springframework.stereotype.Component;

/**
 * <strong>实现功能:</strong>
 * <p>调试你好的业务逻辑实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-18 19:42
 */
@Component
public class HelloManager {
    /**
     * 你好业务逻辑
     * @param name 姓名
     * @param param 参数
     * @return 返回句子
     */
    public String sayHello(String name, String param){
        return "你好，"+name+"！参数："+param;
    }
}
