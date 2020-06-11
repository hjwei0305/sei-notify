package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.EmailAccount;
import com.changhong.sei.notify.dto.EmailMessage;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.NotifyType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-05-25 9:19
 */
public class NotifyControllerTest extends BaseUnitTest {
    @Autowired
    private NotifyController controller;

    @Test
    public void send() {
        NotifyMessage message = new NotifyMessage();
        message.setCanToSender(true);
        message.setContent("测试:SEI_REMIND");
        List<NotifyType> notifyTypes = new ArrayList<>();
        notifyTypes.add(NotifyType.SEI_REMIND);
        message.setNotifyTypes(notifyTypes);
        List<String> receiverIds = new ArrayList<>();
        receiverIds.add("B54E8964-D14D-11E8-A64B-0242C0A8441B");
        message.setReceiverIds(receiverIds);
        message.setSubject("测试SEI站内通知");
        ResultData<String> resultData = controller.send(message);
        LOG.debug(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    /**
     * 生成消息
     * @return 消息
     */
    private EmailMessage builderMessage(){
        EmailMessage message = new EmailMessage();
        message.setSubject("Test 测试发送邮件");
//        message.setSender(new EmailAccount("马超","chao2.ma@changhong.com"));
//        message.setSender(new EmailAccount("马超", ""));
        // message.setContent("Test 测试邮件内容");
        //测试模板
        message.setContentTemplateCode("EMAIL_INQUIRY_PUBLISH");
        Map<String,Object> params = new HashMap<>();
        params.put("supplierName","宝宝");
        params.put("corporationName","baobao");
        params.put("name","123456");
        params.put("code","123456");
        params.put("quoteEndDate","123456");
        params.put("inquiryUserName","123456");
        params.put("inquiryUserContact","123456");
        message.setContentTemplateParams(params);
        List<EmailAccount> receivers = new ArrayList<>();
        receivers.add(new EmailAccount("马超","87540704@qq.com"));
        message.setReceivers(receivers);
        return message;
    }

    @Test
    public void sendEmail() throws Exception{
        controller.sendEmail(builderMessage());
        Thread.sleep(30*1000);
    }
}