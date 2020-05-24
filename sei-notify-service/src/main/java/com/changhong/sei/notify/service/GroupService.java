package com.changhong.sei.notify.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.notify.dao.GroupDao;
import com.changhong.sei.notify.dao.GroupUserDao;
import com.changhong.sei.notify.entity.Group;
import com.changhong.sei.notify.entity.GroupUser;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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

    /**
     * 添加群组用户
     *
     * @param groupUsers 群组用户
     * @return 操作结果
     */
    @Transactional
    public ResultData<String> addGroupUsers(List<GroupUser> groupUsers) {
        if (CollectionUtils.isNotEmpty(groupUsers)) {
            groupUserDao.save(groupUsers);
            return ResultData.success("ok");
        }
        return ResultData.fail("群组用户为空,不能添加");
    }

    /**
     * 删除群组用户
     *
     * @param groupUserIds 群组用户id
     * @return 操作结果
     */
    @Transactional
    public ResultData<String> delGroupUser(List<String> groupUserIds) {
        if (CollectionUtils.isNotEmpty(groupUserIds)) {
            groupUserDao.delete(groupUserIds);
            return ResultData.success("ok");
        }
        return ResultData.fail("删除的群组id不能为空.");
    }

    /**
     * 获取指定群组用户
     *
     * @param groupId 群组id
     * @return 返回指定群组用户对象
     */
    public ResultData<List<GroupUser>> getGroupUsers(String groupId) {
        List<GroupUser> groupUsers = groupUserDao.findListByProperty(GroupUser.FIELD_GROUP_ID, groupId);
        return ResultData.success(groupUsers);
    }

    /**
     * 获取指定用户拥有的群组
     *
     * @param userId 用户id
     * @return 返回指定用户拥有的群组
     */
    public ResultData<Set<String>> getGroupCodes(String userId) {
        Set<String> result = Sets.newHashSet();
        List<Group> groups = groupUserDao.findGroups(userId);
        if (CollectionUtils.isNotEmpty(groups)) {
            result = groups.stream().map(Group::getCode).collect(Collectors.toSet());
        }
        return ResultData.success(result);
    }
}