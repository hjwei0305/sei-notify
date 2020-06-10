package com.changhong.sei.notify.sdk.manager;

import com.changhong.sei.notify.dto.*;
import com.ecmp.context.ContextUtil;
import com.ecmp.enums.UserAuthorityPolicy;
import com.ecmp.enums.UserType;
import com.ecmp.vo.LoginStatus;
import com.ecmp.vo.ResponseData;
import com.ecmp.vo.SessionUser;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-04-21 15:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotifyManagerTest {
    @Autowired
    private NotifyManager notifyManager;

    @Before
    public void before() {
        SessionUser sessionUser = new SessionUser();
        sessionUser.setAccount("admin");
        sessionUser.setTenantCode("10044");
        sessionUser.setUserName("管理员");
        sessionUser.setUserType(UserType.Employee);
        sessionUser.setAuthorityPolicy(UserAuthorityPolicy.GlobalAdmin);
        sessionUser.setLoginTime(new Date());
        sessionUser.setLoginStatus(LoginStatus.success);
        String token = ContextUtil.generateToken(sessionUser);
        sessionUser.setAccessToken(token);
        ContextUtil.setSessionUser(sessionUser);
    }

    @Test
    public void send() {
        NotifyMessage message = new NotifyMessage();
        message.setSubject("测试");
        message.setContent("测试内容");

        message.addReceiverId("B54E8964-D14D-11E8-A64B-0242C0A8441B");
        message.addNotifyType(NotifyType.SMS);

        ResponseData<String> result = notifyManager.send(message);
        System.out.println(result);
    }

    @Test
    public void sendSms() {
        SmsMessage message = new SmsMessage();
        message.setContent("测试内容");
        message.setPhoneNums(Lists.newArrayList("18608081023"));
        ResponseData<String> result = notifyManager.sendSms(message);
        System.out.println(result);
    }

    @Test
    public void sendEmail() {
        EmailMessage message = new EmailMessage();
        message.setSubject("Test 测试发送邮件");
        message.setContent("测试内容");

        message.setReceivers(Lists.newArrayList(new EmailAccount("马超", "87540704@qq.com")));
        ResponseData<String> result = notifyManager.sendEmail(message);
        System.out.println(result);
    }

    @Test
    public void sendEmail1() {
        EmailMessage message = new EmailMessage();
        message.setSubject("Test 测试发送邮件");
        // message.setContent("测试内容");
        //测试模板
        message.setContentTemplateCode("EMAIL_TEMPLATE_REGIST");
        Map<String,Object> params = new HashMap<>();
        params.put("userName","宝宝");
        params.put("account","baobao");
        params.put("password","123456");
        message.setContentTemplateParams(params);
        List<EmailAccount> receivers = new ArrayList<>();
        receivers.add(new EmailAccount("马超","87540704@qq.com"));
        message.setReceivers(receivers);
        ResponseData<String> result = notifyManager.sendEmail(message);
        System.out.println(result);
    }

    @Test
    public void getReceiverIdsByGroup() {
        ResponseData<List<String>> result = notifyManager.getReceiverIdsByGroup("user");
        System.out.println(result);
    }

}