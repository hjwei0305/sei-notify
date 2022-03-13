package com.changhong.sei.notify.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.dto.*;
import com.changhong.sei.notify.manager.ContentBuilder;
import com.changhong.sei.notify.manager.sms.SmsManager;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-21 19:13
 */
public class SmsManagerTest extends BaseUnitTest {

    @Autowired
    private SmsManager manager;
    @Autowired
    private ContentBuilder contentBuilder;

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