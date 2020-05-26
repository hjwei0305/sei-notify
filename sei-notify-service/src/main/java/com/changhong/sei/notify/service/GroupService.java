package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.notify.dao.GroupDao;
import com.changhong.sei.notify.dao.GroupItemDao;
import com.changhong.sei.notify.entity.Group;
import com.changhong.sei.notify.entity.GroupItem;
import com.changhong.sei.notify.service.cust.BasicIntegration;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private GroupItemDao groupUserDao;
    @Autowired
    private BasicIntegration integration;

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
    public ResultData<String> addGroupUsers(List<GroupItem> groupUsers) {
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
    public ResultData<List<GroupItem>> getGroupUsers(String groupId) {
        List<GroupItem> groupUsers = groupUserDao.findListByProperty(GroupItem.FIELD_GROUP_ID, groupId);
        return ResultData.success(groupUsers);
    }

    /**
     * 获取指定用户拥有的群组
     *
     * @param itemCodes 群组项code
     * @return 返回指定用户拥有的群组
     */
    public ResultData<Set<String>> getGroupCodes(Set<String> itemCodes) {
        Set<String> result = Sets.newHashSet();
        List<Group> groups = groupUserDao.findGroups(itemCodes);
        if (CollectionUtils.isNotEmpty(groups)) {
            result = groups.stream().map(Group::getId).collect(Collectors.toSet());
        }
        return ResultData.success(result);
    }

    /**
     * 根据群组获取用户id集合
     *
     * @param groupCode 群组代码
     * @return 用户id集合
     */
    public ResultData<List<String>> getUserIdsByGroup(String groupCode) {
        List<String> result = new ArrayList<>();
        Group group = dao.findByProperty(Group.FIELD_CODE, groupCode);
        if (Objects.nonNull(group)) {
            if (group.getFrozen()) {
                return ResultData.fail(ContextUtil.getMessage("群组[{0}]已被冻结", groupCode));
            }
            Set<String> codeSet;
            ResultData<List<String>> resultData;
            List<GroupItem> items = groupUserDao.findListByProperty(GroupItem.FIELD_GROUP_ID, group.getId());
            if (CollectionUtils.isNotEmpty(items)) {
                switch (group.getCategory()) {
                    case USER:
                        result = items.stream().map(GroupItem::getItemId).collect(Collectors.toList());
                        break;
                    case ORG:

                        break;
                    case POS:
                        codeSet = items.stream().map(GroupItem::getItemCode).collect(Collectors.toSet());
                        resultData = integration.getUserIdsByPosition(codeSet);
                        if (resultData.successful()) {
                            result = resultData.getData();
                        } else {
                            LogUtil.error(resultData.getMessage());
                        }
                        break;
                    case ROLE:
                        codeSet = items.stream().map(GroupItem::getItemCode).collect(Collectors.toSet());
                        resultData = integration.getUserIdsByRole(codeSet);
                        if (resultData.successful()) {
                            result = resultData.getData();
                        } else {
                            LogUtil.error(resultData.getMessage());
                        }
                        break;
                    default:

                }
            }
        }

        return ResultData.success(result);
    }
}