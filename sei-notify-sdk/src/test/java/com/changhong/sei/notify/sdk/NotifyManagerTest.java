package com.changhong.sei.notify.sdk;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.sdk.manager.NotifyManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-25 00:30
 */
public class NotifyManagerTest extends BaseUnitTest {

    @Autowired
    private NotifyManager notifyManager;

    @Test
    public void send() {
        NotifyMessage message = new NotifyMessage();
        message.setSubject("测试");
//        message.setContent("测试内容");
        //测试模板
        message.setContentTemplateCode("EMAIL_TEMPLATE_REGIST");
        Map<String,Object> params = new HashMap<>();
        params.put("userName","宝宝mac");
        params.put("account","baobao");
        params.put("password","123456");
        message.setContentTemplateParams(params);

        ResultData<List<String>> resultData;
        // 按群组获取接收者. 按实际业务场景需要确定
        resultData = notifyManager.getReceiverIdsByGroup("群组代码");
        if (resultData.successful()) {
            message.addReceiverIds(resultData.getData());
        }
        // 按岗位获取接收者. 按实际业务场景需要确定
        resultData = notifyManager.getReceiverIdsByPosition("组织结构代码", "岗位代码");
        if (resultData.successful()) {
            message.addReceiverIds(resultData.getData());
        }
        // 按角色获取接收者. 按实际业务场景需要确定
        resultData = notifyManager.getReceiverIdsByRole("角色代码");
        if (resultData.successful()) {
            message.addReceiverIds(resultData.getData());
        }
        // 直接指定接收用户userId. 按实际业务场景需要确定
        message.addReceiverId("B54E8964-D14D-11E8-A64B-0242C0A8441B");

        // 系统提醒
        message.addNotifyType(NotifyType.SEI_REMIND);
        // 电子邮件
        message.addNotifyType(NotifyType.EMAIL);
        // 手机短信
        message.addNotifyType(NotifyType.SMS);
        // 微信小程序消息
        message.addNotifyType(NotifyType.MiniApp);

        // 发送消息,并返回发送结果
        ResultData<String> result = notifyManager.send(message);
        LOG.info(result.toString());
    }

    @Test
    public void getReceiverIdsByGroup() {
        ResultData<List<String>> result = notifyManager.getReceiverIdsByGroup("user");
        System.out.println(result);
    }
}