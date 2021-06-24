package com.changhong.sei.notify.entity;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.core.entity.ITenant;
import com.changhong.sei.notify.dto.Priority;
import com.changhong.sei.notify.dto.TargetType;
import com.changhong.sei.notify.entity.cust.BulletinCust;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 实现功能：公告消息
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-19 17:08
 */
@Entity()
@Table(name = "bulletin")
@Access(AccessType.FIELD)
public class Bulletin extends BulletinCust implements ITenant {
    private static final long serialVersionUID = 1029465462675785527L;

    /**
     * 租户代码
     */
    @Column(name = "tenant_code")
    private String tenantCode;
    /**
     * 消息id
     */
    @Column(name = "msg_id")
    private String msgId;
    /**
     * 消息主题
     */
    @Column(name = "subject", nullable = false)
    protected String subject;
    /**
     * 目标类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private TargetType targetType = TargetType.SYSTEM;
    /**
     * 目标对象Value
     * targetType如果是TargetType.PERSONAL, 则为userId
     */
    @Column(name = "target_value")
    private String targetValue;

    /**
     * 目标对象name
     */
    @Column(name = "target_name")
    private String targetName;
    /**
     * 优先级
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority_")
    @JsonSerialize(using = EnumJsonSerializer.class)
    protected Priority priority = Priority.General;

    /**
     * 是否发布
     */
    @Column(name = "is_publish")
    private Boolean publish = Boolean.FALSE;
    /**
     * 发布时间时间
     */
    @Column(name = "publish_date")
    protected LocalDateTime publishDate;
    /**
     * 发布人
     */
    @Column(name = "publish_user_account")
    private String publishUserAccount;
    /**
     * 发布人
     */
    @Column(name = "publish_user_name")
    private String publishUserName;
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
     * 撤销时间
     */
    @Column(name = "cancel_date")
    private LocalDateTime cancelDate;
    /**
     * 是否有效
     */
    @Column(name = "is_effective")
    protected Boolean effective = Boolean.TRUE;
    /**
     * 生效时间
     */
    @Column(name = "effective_date")
    private LocalDate effectiveDate;
    /**
     * 失效时间
     */
    @Column(name = "invalid_date")
    private LocalDate invalidDate;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public String getTenantCode() {
        return tenantCode;
    }

    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishUserAccount() {
        return publishUserAccount;
    }

    public void setPublishUserAccount(String publishUserAccount) {
        this.publishUserAccount = publishUserAccount;
    }

    public String getPublishUserName() {
        return publishUserName;
    }

    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
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

    public Boolean getEffective() {
        return effective;
    }

    public void setEffective(Boolean effective) {
        this.effective = effective;
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
}
