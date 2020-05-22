package com.changhong.sei.notify.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.notify.entity.Group;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 群组(Group)数据库访问类
 *
 * @author sei
 * @since 2020-05-22 11:04:12
 */
@Repository
public interface GroupDao extends BaseEntityDao<Group> {

    /**
     * 更新冻结状态
     *
     * @param ids    群组id
     * @param frozen 冻结状态
     * @return 返回更新记录数
     */
    @Modifying
    @Query("update Group s set s.frozen=:frozen where s.id in (:ids)")
    int updateFrozen(@Param("ids") List<String> ids, @Param("frozen") Boolean frozen);
}