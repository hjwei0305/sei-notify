package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.entity.Message;
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
}