package com.changhong.sei.notify.manager;

import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.dto.BaseMessageDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-23 22:53
 */
public class MessageManagerTest extends BaseUnitTest {

    @Autowired
    private MessageManager messageManager;

    @Test
    public void unreadCount() {
        Long num = messageManager.unreadCount();
        System.out.println(num);
    }

    @Test
    public void getTargetCodeByUser() {
    }

    @Test
    public void unreadData() {
        OperateResultWithData<Map<String, List<BaseMessageDto>>> result = messageManager.unreadData();
        System.out.println(result);
    }

    @Test
    public void read() {
    }

    @Test
    public void detail() {
    }

    @Test
    public void getFirstUnreadBulletin() {
    }

    @Test
    public void findBulletinByPage4User() {
    }
}