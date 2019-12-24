package com.changhong.sei.notify.manager;

import com.changhong.sei.notify.dao.ContentTemplateDao;
import com.changhong.sei.notify.entity.ContentTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * <strong>实现功能:</strong>
 * <p>内容模板业务逻辑实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 15:28
 */
@Component
public class ContentTemplateManager {
    @Autowired
    private ContentTemplateDao dao;

    /**
     * 获取所有内容模板
     * @return 内容模板清单
     */
    public List<ContentTemplate> findAll(){
        return dao.findAll();
    }

    /**
     * 保存一个内容模板
     * @param contentTemplate 内容模板
     * @return 保存结果
     */
    public ContentTemplate save(ContentTemplate contentTemplate){
        return dao.save(contentTemplate);
    }

    /**
     * 通过Id获取内容模板
     * @param id Id标识
     * @return 内容模板
     */
    public ContentTemplate findOne(String id){
        Optional<ContentTemplate> contentTemplate = dao.findById(id);
        return contentTemplate.orElse(null);
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
