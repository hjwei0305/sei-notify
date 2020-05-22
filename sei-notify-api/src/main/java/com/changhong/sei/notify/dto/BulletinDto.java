package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-23 15:29
 */
public class BulletinDto extends BaseMessageDto {
    private static final long serialVersionUID = 2575128462397611147L;

    /**
     * 是否发布
     */
    private Boolean release = Boolean.FALSE;
    /**
     * 发布人id
     */
    private String releaseUserAccount;
    private String releaseUserName;
    /**
     * 发布时间
     */
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_TIME_FORMAT)
    private Date releaseDate;
    /**
     * 撤销人id
     */
    private String cancelUserAccount;
    private String cancelUserName;
    /**
     * 撤销时间
     */
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_TIME_FORMAT)
    private Date cancelDate;
    /**
     * 生效时间
     */
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    private Date effectiveDate;
    /**
     * 失效时间
     */
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_DATE_FORMAT)
    private Date invalidDate;

    /**
     * 目标对象code
     */
    private String targetCode;

    /**
     * 目标对象name
     */
    private String targetName;

    /**
     * 目标类型
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    private TargetType targetType;
    /**
     * 附件id
     */
    private Set<String> docIds;
    private Boolean read = Boolean.FALSE;

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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
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

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
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
