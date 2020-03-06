package com.changhong.sei.notify.apitest;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.api.NotifyApi;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.NotifyType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现功能: 测试消息通知服务
 */
public class NotifyApiTest extends BaseUnitTest {
    @Autowired
    private NotifyApi notifyApi;

    /**
     * 构造一个消息
     * @return 消息
     */
    private NotifyMessage constructMessage(){
        NotifyMessage message = new NotifyMessage();
        List<NotifyType> notifyTypes = new ArrayList<>();
        notifyTypes.add(NotifyType.EMAIL);
        message.setNotifyTypes(notifyTypes);
        // 设置发件人用户Id
        message.setSenderId("B54E8964-D14D-11E8-A64B-0242C0A8441B");
        message.setSubject("测试平台消息发送-test subject");
        message.setContent("测试平台消息发送-test content");
        List<String> receiverIds = new ArrayList<>();
        // 设置收件人用户Id清单
        receiverIds.add("E4D36DB5-E173-11E8-9ABE-0242C0A8441B");
        receiverIds.add("EF2E29F8-DE1C-11E7-AD2C-0242C0A84202");
        message.setReceiverIds(receiverIds);
        //测试模板
        message.setContentTemplateCode("EMAIL_TEMPLATE_REGIST");
        Map<String,Object> params = new HashMap<>();
        params.put("userName","程序员");
        params.put("account","devUser");
        params.put("password", "123456");
        message.setContentTemplateParams(params);
        return message;
    }

    @Test
    public void send(){
        notifyApi.send(constructMessage());
    }
}
