package com.changhong.sei.notify.service;

import com.changhong.sei.notify.BaseUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-18 11:21
 */
public class HelloServiceTest extends BaseUnitTest {
    @Autowired
    private HelloService service;

    @Test
    public void sayHello() {
        String name = "王锦光";
        String helloStr = service.sayHello(name);
        System.out.println(helloStr);
    }
}