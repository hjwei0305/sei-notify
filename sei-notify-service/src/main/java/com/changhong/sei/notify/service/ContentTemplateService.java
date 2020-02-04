package com.changhong.sei.notify.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.notify.dao.ContentTemplateDao;
import com.changhong.sei.notify.entity.ContentTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <strong>实现功能:</strong>
 * <p>内容模板业务逻辑实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 15:28
 */
@Service
public class ContentTemplateService extends BaseEntityService<ContentTemplate> {
    @Autowired
    private ContentTemplateDao dao;

    @Override
    protected BaseEntityDao<ContentTemplate> getDao() {
        return dao;
    }

    /**
     * 通过代码获取内容模板
     * @param code 代码
     * @return 内容模板
     */
    public ContentTemplate findByCode(String code){
        return dao.findByCode(code);
    }
}
