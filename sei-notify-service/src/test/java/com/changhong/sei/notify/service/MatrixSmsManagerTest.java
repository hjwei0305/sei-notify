package com.changhong.sei.notify.service;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.dto.SendMessage;
import com.changhong.sei.notify.manager.sms.MatrixSmsManager;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-21 19:13
 */
public class MatrixSmsManagerTest extends BaseUnitTest {

    @Autowired
    private MatrixSmsManager service;

    @Test
    public void send() {
        SendMessage msg = new SendMessage();
        msg.setContent("测试短信Test");
        msg.setSubject("测试");
        UserNotifyInfo info = new UserNotifyInfo();
        info.setMobile("18608081023");
        msg.setReceivers(Lists.newArrayList(info));
        service.send(msg);
    }

    @Test
    public void sign() {
    }
}