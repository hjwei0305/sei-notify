package com.changhong.sei.notify.service;

import com.changhong.sei.notify.entity.Remind;
import com.changhong.sei.notify.dao.RemindDao;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;


/**
 * 提醒信息(Remind)业务逻辑实现类
 *
 * @author sei
 * @since 2020-05-22 18:09:16
 */
@Service("remindService")
public class RemindService extends BaseEntityService<Remind> {
    @Autowired
    private RemindDao dao;

    @Override
    protected BaseEntityDao<Remind> getDao() {
        return dao;
    }

    /**
     * 获取指定用户未读提醒数
     *
     * @param tenantCode 租户代码
     * @param userId     用户id
     * @return 未读提醒数
     */
    public Long countUnRead(String tenantCode, String userId) {
        return dao.countUnRead(tenantCode, userId);
    }
}