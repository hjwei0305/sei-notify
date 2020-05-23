package com.changhong.sei.notify.service;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.dto.Priority;
import com.changhong.sei.notify.dto.TargetType;
import com.changhong.sei.notify.entity.Bulletin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

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
        bulletin.setSubject("测试主题123");
        bulletin.setEffectiveDate(date);
        bulletin.setInvalidDate(date);
        bulletin.setTargetType(TargetType.GROUP);
        bulletin.setTargetCode("123");
        bulletin.setTargetName("123");
        bulletin.setPriority(Priority.General);

        service.saveBulletin(bulletin, content);
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