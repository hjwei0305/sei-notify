package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.NotifyType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
}