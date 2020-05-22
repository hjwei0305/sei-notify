package com.changhong.sei.notify.service;

import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.notify.entity.Group;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-22 14:02
 */
public class GroupServiceTest extends BaseUnitTest {

    @Autowired
    private GroupService service;

    @Test
    public void save() {
        Group group = new Group();
        group.setCode("aaassaa");
        group.setName("aaasasa");
        group.setRank(1);
        OperateResultWithData result = service.save(group);
        System.out.println(result);
    }

    @Test
    public void frozen() {
    }

    @Test
    public void addGroupUsers() {
    }

    @Test
    public void delGroupUser() {
    }

    @Test
    public void getGroupUsers() {
    }
}