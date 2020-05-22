package com.changhong.sei.notify.dao;

import com.changhong.sei.notify.entity.Remind;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 提醒信息(Remind)数据库访问类
 *
 * @author sei
 * @since 2020-05-22 18:09:16
 */
@Repository
public interface RemindDao extends BaseEntityDao<Remind> {

    /**
     * 获取指定用户未读提醒数
     *
     * @param tenantCode 租户代码
     * @param userId     用户id
     * @return 未读提醒数
     */
    @Query("select count(t) from Remind t where t.tenantCode = :tenantCode and t.userId = :userId and t.read = 0")
    Long countUnRead(@Param("tenantCode") String tenantCode, @Param("userId") String userId);
}