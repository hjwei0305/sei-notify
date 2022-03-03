package com.changhong.sei.notify.dao;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.test.BaseUnit5Test;
import com.changhong.sei.notify.dto.TargetType;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2022-03-03 13:06
 */
class MessageUserDaoTest extends BaseUnit5Test {

    @Autowired
    private MessageUserDao messageUserDao;

    @Test
    void messageUserDao() {
        String userId = ContextUtil.getUserId();
        Set<String> targetValues = Sets.newHashSet();
        // 添加用户id,获取个人点对点消息
        targetValues.add(userId);
        // 添加系统消息
        targetValues.add(TargetType.SYSTEM.name());
        Long count = messageUserDao.getUnreadCount(userId, targetValues);
        System.out.println(count);
    }
}