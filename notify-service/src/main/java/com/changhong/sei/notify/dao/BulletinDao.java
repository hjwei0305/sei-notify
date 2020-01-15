package com.changhong.sei.notify.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.notify.entity.Bulletin;
import org.springframework.stereotype.Repository;

/**
 * 实现功能：消息通告数据访问接口
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-20 14:33
 */
@Repository
public interface BulletinDao extends BaseEntityDao<Bulletin>, BulletinExtDao {
}
