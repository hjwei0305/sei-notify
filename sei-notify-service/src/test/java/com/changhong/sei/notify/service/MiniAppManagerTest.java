package com.changhong.sei.notify.service;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.EmailAccount;
import com.changhong.sei.notify.dto.EmailMessage;
import com.changhong.sei.notify.dto.SendMessage;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.manager.email.EmailManager;
import com.changhong.sei.notify.manager.miniapp.MiniAppManager;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-10 9:07
 */
public class MiniAppManagerTest extends BaseUnitTest {
    @Autowired
    private MiniAppManager miniAppManager;

    @Test
    public void sendMiniApp(){
        String content = "{\n" +
                "    \"templateId\":\"QzxWd8NmbyeO6Yi9RobDRD6at5xk4D_yDUdLQxKzVpg\",\n" +
                "    \"page\":\"pages/message/messageDetail?cmscontentid=123456\",\n" +
                "    \"data\":[\n" +
                "       {\n" +
                "            \"name\":\"thing1\",\n" +
                "            \"desc\":\"通知类型\",\n" +
                "            \"value\":\"通知\",\n" +
                "            \"color\" :\"\"\n" +
                "       },\n" +
                "       {\n" +
                "            \"name\":\"thing2\",\n" +
                "            \"desc\":\"通知内容\",\n" +
                "            \"value\":\"详情\",\n" +
                "            \"color\" :\"\"\n" +
                "       },\n" +
                "       {\n" +
                "            \"name\":\"time3\",\n" +
                "            \"desc\":\"通知时间\",\n" +
                "            \"value\":\"2020-06-02 13:44:49\",\n" +
                "            \"color\" :\"\"\n" +
                "       }\n" +
                "    ]\n" +
                "}";
        SendMessage sendMessage = new SendMessage();
        sendMessage.setContent(content);
        List<UserNotifyInfo> receivers = new ArrayList<>();
        UserNotifyInfo userNotifyInfo = new UserNotifyInfo();
        userNotifyInfo.setMiniProgramOpenId("oQxqY5N1p770bNI76dXUwTxMR4P8");
        userNotifyInfo.setUserId("1D676C93-5F79-11EA-AAAA-0242C0A84410");
        userNotifyInfo.setUserAccount("pcp001");
        receivers.add(userNotifyInfo);
        sendMessage.setReceivers(receivers);
        ResultData<String> resultData = miniAppManager.send(sendMessage);
        LOG.debug(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}