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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
     * 发布人
     */
    @Column(name = "release_user_account")
    private String releaseUserAccount;
    /**
     * 发布人
     */
    @Column(name = "release_user_name")
    private String releaseUserName;
    /**
     * 发布时间
     */
//    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_TIME_FORMAT)
    @Column(name = "release_date")
    private LocalDateTime releaseDate;
    /**
     * 撤销人id
     */
    @Column(name = "cancel_user_account", length = 36)
    private String cancelUserAccount;
    /**
     * 撤销人id
     */
    @Column(name = "cancel_user_name", length = 36)
    private String cancelUserName;

    /**
     * 目标对象code
     */
    @Column(name = "target_code", length = 36)
    private String targetCode;

    /**
     * 目标对象name
     */
    @Column(name = "target_name", length = 36)
    private String targetName;

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
//    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_TIME_FORMAT)
    @Column(name = "cancel_date")
    private LocalDateTime cancelDate;
    /**
     * 生效时间
     */
//    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    @Column(name = "effective_date")
    private LocalDate effectiveDate;
    /**
     * 失效时间
     */
//    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    @Column(name = "invalid_date")
    private LocalDate invalidDate;
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

    public String getReleaseUserAccount() {
        return releaseUserAccount;
    }

    public void setReleaseUserAccount(String releaseUserAccount) {
        this.releaseUserAccount = releaseUserAccount;
    }

    public String getReleaseUserName() {
        return releaseUserName;
    }

    public void setReleaseUserName(String releaseUserName) {
        this.releaseUserName = releaseUserName;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCancelUserAccount() {
        return cancelUserAccount;
    }

    public void setCancelUserAccount(String cancelUserAccount) {
        this.cancelUserAccount = cancelUserAccount;
    }

    public String getCancelUserName() {
        return cancelUserName;
    }

    public void setCancelUserName(String cancelUserName) {
        this.cancelUserName = cancelUserName;
    }

    public LocalDateTime getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDateTime cancelDate) {
        this.cancelDate = cancelDate;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDate getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(LocalDate invalidDate) {
        this.invalidDate = invalidDate;
    }

    public Set<String> getDocIds() {
        return docIds;
    }

    public void setDocIds(Set<String> docIds) {
        this.docIds = docIds;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }
}
