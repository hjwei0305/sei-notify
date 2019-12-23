package com.changhong.sei.notify.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * <strong>实现功能:</strong>
 * <p>内容模板</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 10:40
 */
@Access(AccessType.FIELD)
@Entity
@Table(name = "content_template")
@DynamicInsert
@DynamicUpdate
public class ContentTemplate extends BaseDaoEntity{
    /**
     * 代码
     */
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    /**
     * 名称
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 内容模板
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", nullable = false)
    private String content;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
