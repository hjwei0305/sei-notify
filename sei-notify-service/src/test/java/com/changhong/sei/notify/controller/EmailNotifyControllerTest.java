package com.changhong.sei.notify.controller;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.dto.EmailAccount;
import com.changhong.sei.notify.dto.EmailMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-13 14:29
 */
public class EmailNotifyControllerTest extends BaseUnitTest {
    @Autowired
    private EmailNotifyController controller;

    /**
     * 生成消息
     * @return 消息
     */
    private EmailMessage builderMessage(){
        EmailMessage message = new EmailMessage();
        message.setSubject("Test 测试发送邮件");
        message.setSender(new EmailAccount("王锦光","wangjg@changhong.com"));
        // message.setContent("Test 测试邮件内容");
        //测试模板
        message.setContentTemplateCode("EMAIL_TEMPLATE_REGIST");
        Map<String,Object> params = new HashMap<>();
        params.put("userName","宝宝");
        params.put("account","baobao");
        params.put("password","123456");
        message.setContentTemplateParams(params);
        List<EmailAccount> receivers = new ArrayList<>();
        receivers.add(new EmailAccount("王锦光","elegancelight@qq.com"));
        message.setReceivers(receivers);
        return message;
    }

    @Test
    public void sendEmail() throws Exception{
        controller.sendEmail(builderMessage());
        //Thread.sleep(30*1000);
    }
}