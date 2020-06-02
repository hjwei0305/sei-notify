package com.changhong.sei.notify.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.notify.entity.ContentTemplate;
import com.changhong.sei.notify.entity.UserMiniAppPushTime;
import org.springframework.stereotype.Repository;

/**
 * <strong>实现功能:</strong>
 * <p>应用模块数据访问接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2017-10-20 9:41
 */
@Repository
public interface UserMiniAppPushTimeDao extends BaseEntityDao<UserMiniAppPushTime> {

    /**
     * 通过小程序openId获取小程序推送次数
     * @param openId 小程序openId
     * @return 小程序推送次数
     */
    UserMiniAppPushTime findByMiniProgramOpenId(String openId);

}
