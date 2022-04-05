package com.changhong.sei.notify.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.notify.entity.Group;
import com.changhong.sei.notify.entity.GroupItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 群组(GroupUser)数据库访问类
 *
 * @author sei
 * @since 2020-05-22 11:04:12
 */
@Repository
public interface GroupItemDao extends BaseEntityDao<GroupItem> {

    /**
     * 根据群组项代码获取群组
     *
     * @param itemIds 群组项Id
     * @return 群组集合
     */
    @Query("select t from Group t join GroupItem u on t.id = u.groupId and u.itemId in :itemIds where t.frozen = false")
    List<Group> findGroups(@Param("itemIds") Set<String> itemIds);
}