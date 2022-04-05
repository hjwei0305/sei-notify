package com.changhong.sei.notify.service;

import com.changhong.sei.core.config.properties.mock.MockUserProperties;
import com.changhong.sei.core.context.mock.LocalMockUser;
import com.changhong.sei.core.context.mock.MockUser;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.dto.*;
import com.changhong.sei.notify.manager.ContentBuilder;
import com.changhong.sei.notify.manager.sms.SmsManager;
import com.changhong.sei.util.thread.ThreadLocalHolder;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-21 19:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsManagerTest {
    @Autowired
    public MockUserProperties properties;
    @Autowired
    private SmsManager manager;
    @Autowired
    private ContentBuilder contentBuilder;

    @BeforeClass
    public static void setup() {
        // 初始化
        ThreadLocalHolder.begin();

        System.out.println("开始进入单元测试.......");
    }

    @Before
    public void mock() {
        MockUser mockUser = new LocalMockUser();
        System.out.println("当前模拟用户: " + mockUser.mockUser(properties.getTenantCode(), properties.getAccount()));
    }

    @Test
    public void send() {
        SmsMessage smsMessage = new SmsMessage();
        // TODO 需要在notify模块中预制
        smsMessage.setContentTemplateCode("AUTH_SMS_LOGIN");
        Map<String, Object> params = new HashMap<>();
        params.put("code", "123456");
        smsMessage.setContentTemplateParams(params);

        // 生成消息
        contentBuilder.build(smsMessage);
        String content = smsMessage.getContent();

        SendMessage msg = new SendMessage();
        msg.setContent(content);
        UserNotifyInfo info = new UserNotifyInfo();
        info.setMobile("18608081023");
        msg.setReceivers(Lists.newArrayList(info));
        ResultData<String> resultData = manager.send(msg);
        System.out.println(resultData);
    }

    @Test
    public void sign() {
    }
}