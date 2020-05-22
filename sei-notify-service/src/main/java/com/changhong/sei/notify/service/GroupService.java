package com.changhong.sei.notify.service;

import com.changhong.sei.notify.dao.GroupUserDao;
import com.changhong.sei.notify.entity.Group;
import com.changhong.sei.notify.dao.GroupDao;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 群组(Group)业务逻辑实现类
 *
 * @author sei
 * @since 2020-05-22 11:04:12
 */
@Service("groupService")
public class GroupService extends BaseEntityService<Group> {
    @Autowired
    private GroupDao dao;
    @Autowired
    private GroupUserDao groupUserDao;

    @Override
    protected BaseEntityDao<Group> getDao() {
        return dao;
    }

}