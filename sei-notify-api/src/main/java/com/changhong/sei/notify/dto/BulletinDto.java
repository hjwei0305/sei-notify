package com.changhong.sei.notify.dto;

import com.changhong.sei.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-23 15:29
 */
public class BulletinDto extends MessageDto {
    private static final long serialVersionUID = 2575128462397611147L;

    /**
     * 消息id
     */
    private String msgId;
    /**
     * 撤销人id
     */
    private String cancelUserAccount;
    /**
     * 撤销人id
     */
    private String cancelUserName;
    /**
     * 撤销时间
     */
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_TIME_FORMAT)
    private LocalDateTime cancelDate;
    /**
     * 生效时间
     */
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    private LocalDate effectiveDate;
    /**
     * 失效时间
     */
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    private LocalDate invalidDate;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
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
