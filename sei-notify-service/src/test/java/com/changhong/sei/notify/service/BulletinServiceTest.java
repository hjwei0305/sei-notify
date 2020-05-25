package com.changhong.sei.notify.service;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.dto.Priority;
import com.changhong.sei.notify.dto.TargetType;
import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.Message;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-23 07:59
 */
public class BulletinServiceTest extends BaseUnitTest {

    @Autowired
    private BulletinService service;

    @Test
    public void saveBulletin() {
        LocalDate date = LocalDate.now();
        String content = "测试内容abc1234";
        Bulletin bulletin = new Bulletin();
        bulletin.setEffectiveDate(date);
        bulletin.setInvalidDate(date);

        Message message = new Message();
        message.setSubject("测试主题123");
        message.setContent(content);
        message.setTargetType(TargetType.GROUP);
        message.setTargetValue("123");
        message.setTargetName("123");
        message.setPriority(Priority.General);

        service.saveBulletin(bulletin, message);
    }

    @Test
    public void releaseBulletin() {
    }

    @Test
    public void cancelBulletin() {
    }

    @Test
    public void deleteBulletin() {
    }

    @Test
    public void getBulletin() {
    }
}