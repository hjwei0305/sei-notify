package com.changhong.sei.notify.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.notify.entity.BulletinUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-20 14:33
 */
@Repository
public interface BulletinUserDao extends BaseEntityDao<BulletinUser> {

    /**
     * 按通告id删除用户阅读数据
     * 通告撤销时使用
     *
     * @param bulletinIds 通告id
     */
    @Modifying
    void deleteByBulletinIdIn(Collection<String> bulletinIds);

    BulletinUser findByBulletinIdAndUserId(String bulletinId, String userId);
}
