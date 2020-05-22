package com.changhong.sei.notify.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.notify.dao.ContentBodyDao;
import com.changhong.sei.notify.dao.RemindDao;
import com.changhong.sei.notify.entity.ContentBody;
import com.changhong.sei.notify.entity.Remind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 提醒信息(Remind)业务逻辑实现类
 *
 * @author sei
 * @since 2020-05-22 18:09:16
 */
@Service("contentBodyService")
public class ContentBodyService extends BaseEntityService<ContentBody> {
    @Autowired
    private ContentBodyDao dao;

    @Override
    protected BaseEntityDao<ContentBody> getDao() {
        return dao;
    }

}