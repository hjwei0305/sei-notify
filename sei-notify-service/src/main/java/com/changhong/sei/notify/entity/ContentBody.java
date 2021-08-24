package com.changhong.sei.notify.entity;

import com.changhong.sei.core.entity.BaseEntity;

import javax.persistence.*;

/**
 * 实现功能：
 * 内容主体
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-19 16:25
 */
@Entity
@Table(name = "content_body")
public class ContentBody extends BaseEntity {
    private static final long serialVersionUID = 6924641723249062630L;

    /**
     * 内容
     */
    // @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content_")
    private String content;

    public ContentBody() {
    }

    public ContentBody(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
