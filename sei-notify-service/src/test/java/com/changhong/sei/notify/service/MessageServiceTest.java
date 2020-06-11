package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.entity.Message;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-25 17:16
 */
public class MessageServiceTest extends BaseUnitTest {

    @Autowired
    private MessageService service;

    @Test
    public void getTargetValueByUser() {
        Set<String> datas = service.getTargetValueByUser(ContextUtil.getSessionUser());
        System.out.println(datas);
    }

    @Test
    public void getUnreadCount() {
        Long datas = service.getUnreadCount(ContextUtil.getSessionUser());
        System.out.println(datas);
    }

    @Test
    public void getFirstUnreadMessage() {
        Message datas = service.getFirstUnreadMessage(ContextUtil.getSessionUser());
        System.out.println(datas);
    }

    @Test
    public void updateReadState() {
        String msgId = "20630940-A148-11EA-81A4-0242C0A8460B";
        ResultData<String> result = service.updateReadState(Sets.newHashSet(msgId), false);
        System.out.println(result);
    }
}