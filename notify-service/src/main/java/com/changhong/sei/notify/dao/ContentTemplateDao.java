package com.changhong.sei.notify.dao;

import com.changhong.sei.notify.entity.ContentTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <strong>实现功能:</strong>
 * <p>应用模块数据访问接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2017-10-20 9:41
 */
@Repository
public interface ContentTemplateDao extends JpaRepository<ContentTemplate, String> {
    /**
     * 通过代码获取内容模板
     * @param code 代码
     * @return 内容模板
     */
    ContentTemplate findByCode(String code);
}
