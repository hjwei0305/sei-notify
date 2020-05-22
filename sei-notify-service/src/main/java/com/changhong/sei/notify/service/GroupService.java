package com.changhong.sei.notify.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.notify.dao.GroupDao;
import com.changhong.sei.notify.dao.GroupUserDao;
import com.changhong.sei.notify.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    /**
     * 冻结
     *
     * @param ids 主键
     * @return 返回操作结果对象
     */
    @Transactional
    public ResultData<String> frozen(List<String> ids, Boolean frozen) {
        dao.updateFrozen(ids, frozen);
        return ResultData.success("ok");
    }
}