package com.changhong.sei.notify.service;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.manager.NotifyManagerContext;
import com.changhong.sei.notify.dto.SendMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-18 20:46
 */
public class NotifyManagerTest extends BaseUnitTest {
    @Autowired
    private NotifyManagerContext context;

    @Test
    public void send(){
        String notifyType = "SMS";
        SendMessage message = new SendMessage();
        message.setContent("测试一个短信");
        context.send(notifyType, message);
    }
}
