package com.changhong.sei.notify.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.core.entity.ITenant;

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
public class Bulletin extends BaseEntity implements ITenant {
    private static final long serialVersionUID = 1029465462675785527L;
    public static final String FIELD_RELEASE = "release";

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
}
