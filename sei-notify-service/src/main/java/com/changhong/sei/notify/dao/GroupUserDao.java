package com.changhong.sei.notify.dao;

import com.changhong.sei.notify.entity.Group;
import com.changhong.sei.notify.entity.GroupUser;
import com.changhong.sei.core.dao.BaseEntityDao;
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
public interface GroupUserDao extends BaseEntityDao<GroupUser> {

    /**
     * 根据用户id获取群组
     *
     * @param userId 用户id
     * @return 群组集合
     */
    @Query("select t from Group t join GroupUser u on t.id = u.groupId and u.userId = :userId where t.frozen = 0")
    List<Group> findGroups(@Param("userId") String userId);
}