package com.changhong.sei.notify.service;

import com.changhong.com.sei.core.test.BaseUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-18 20:46
 */
public class NotifyServiceTest extends BaseUnitTest {
    @Autowired
    private NotifyServiceContext context;

    @Test
    public void send(){
        String notifyType = "SMS";
        SendMessage message = new SendMessage();
        message.setContent("测试一个短信");
        context.send(notifyType, message);
    }
}
