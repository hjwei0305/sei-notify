package com.changhong.sei.notify.entity;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.notify.dto.Priority;
import com.chonghong.sei.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * 实现功能：公告消息基类
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-20 14:22
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class BaseMessage extends BaseEntity {
    private static final long serialVersionUID = 8454048674835991508L;
    public static final String FIELD_DEL = "del";
    /**
     * 消息主题
     */
    @Column(name = "subject", nullable = false)
    protected String subject;
    /**
     * 内容id
     */
    @Column(name = "content_id", length = 36)
    protected String contentId;
    /**
     * 优先级
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority_")
    @JsonSerialize(using = EnumJsonSerializer.class)
    protected Priority priority = Priority.General;
    /**
     * 是否删除
     */
    @Column(name = "is_del")
    protected Boolean del = Boolean.FALSE;
    /**
     * 删除时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_TIME_FORMAT)
    @Column(name = "del_date")
    protected Date delDate;
    /**
     * 删除人id
     */
    @Column(name = "del_user_id", length = 36)
    protected String delUserId;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public Date getDelDate() {
        return delDate;
    }

    public void setDelDate(Date delDate) {
        this.delDate = delDate;
    }

    public String getDelUserId() {
        return delUserId;
    }

    public void setDelUserId(String delUserId) {
        this.delUserId = delUserId;
    }

}
