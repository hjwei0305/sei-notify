package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.entity.Remind;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-23 14:36
 */
public class RemindServiceTest extends BaseUnitTest {

    @Autowired
    private RemindService service;

    @Test
    public void save() {
        Remind remind = new Remind();

        remind.setSubject("测试提醒");
        remind.setContentId("30703734-9BF3-11EA-8A1C-0242C0A8460B");
        SessionUser user = ContextUtil.getSessionUser();
        remind.setUserId(user.getUserId());
        remind.setUserName(user.getUserName());
        remind.setUserAccount(user.getAccount());
        remind.setUserType(user.getUserType());
        remind.setRemindDate(LocalDateTime.now());
        remind.setOrigin("TEST");

        service.save(remind);
    }
}