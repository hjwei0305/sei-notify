package com.changhong.sei.notify.entity;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.notify.dto.TargetType;
import com.changhong.sei.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mysql.cj.MysqlType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 实现功能：公告消息
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-19 17:08
 */
@Entity()
@Table(name = "bulletin")
@Access(AccessType.FIELD)
public class Bulletin extends BaseMessage {
    private static final long serialVersionUID = 1029465462675785527L;
    public static final String FIELD_RELEASE = "release";

    /**
     * 是否发布
     */
    @Column(name = "is_release")
    private Boolean release = Boolean.FALSE;
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
     * 撤销人id
     */
    @Column(name = "cancel_user_id", length = 36)
    private String cancelUserId;

    /**
     * 发布类型code
     */
    @Column(name = "tag_code", length = 36)
    private String tagCode;

    /**
     * 发布类型name
     */
    @Column(name = "tag_name", length = 36)
    private String tagName;

    /**
     * 目标类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private TargetType targetType;

    /**
     * 撤销时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_TIME_FORMAT)
    @Column(name = "cancel_date")
    private Date cancelDate;
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
     * 附件id
     */
    @Transient
    private Set<String> docIds;

    public Boolean getRelease() {
        return release;
    }

    public void setRelease(Boolean release) {
        this.release = release;
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

    public String getCancelUserId() {
        return cancelUserId;
    }

    public void setCancelUserId(String cancelUserId) {
        this.cancelUserId = cancelUserId;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
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

    public Set<String> getDocIds() {
        return docIds;
    }

    public void setDocIds(Set<String> docIds) {
        this.docIds = docIds;
    }

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }
}
