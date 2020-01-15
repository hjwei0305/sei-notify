package com.changhong.sei.notify.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.core.entity.json.EnumJsonSerializer;
import com.changhong.sei.notify.dto.Priority;
import com.chonghong.sei.enums.UserType;
import com.chonghong.sei.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * 实现功能：
 * 通告用户视图
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-19 17:35
 */
@Entity
@Table(name = "v_bulletin_user")
public class BulletinUserView extends BaseEntity {
    private static final long serialVersionUID = -7194060511283609572L;
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
     * 发布人id
     */
    @Column(name = "release_user_id", length = 36)
    private String releaseUserId;
    /**
     * 发布时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_TIME_FORMAT)
    @Column(name = "release_date")
    private Date releaseDate;
    /**
     * 生效时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    @Column(name = "effective_date")
    private Date effectiveDate;
    /**
     * 失效时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    @Column(name = "invalid_date")
    private Date invalidDate;
    /**
     * 用户id
     */
    @Column(name = "user_id", length = 36)
    private String userId;
    /**
     * 用户类型
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_type", length = 1)
    private UserType userType;
    /**
     * 是否阅读
     */
    @Column(name = "is_read")
    private Boolean read = Boolean.FALSE;
    /**
     * 阅读时间
     */
    @Column(name = "read_date")
    private Date readDate;

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

    public String getReleaseUserId() {
        return releaseUserId;
    }

    public void setReleaseUserId(String releaseUserId) {
        this.releaseUserId = releaseUserId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }
}
