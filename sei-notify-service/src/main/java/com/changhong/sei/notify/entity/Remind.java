package com.changhong.sei.notify.entity;

import java.util.Date;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ITenant;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 提醒信息(Remind)实体类
 *
 * @author sei
 * @since 2020-05-22 18:09:16
 */
@Entity
@Table(name = "remind")
@DynamicInsert
@DynamicUpdate
public class Remind extends BaseAuditableEntity implements ITenant, Serializable {
    private static final long serialVersionUID = -32108204849583924L;
    /**
     * 租户代码
     */
    @Column(name = "tenant_code")
    private String tenantCode;
    /**
     * 内容id
     */
    @Column(name = "content_id")
    private String contentId;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 用户账号
     */
    @Column(name = "user_account")
    private String userAccount;
    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 用户类型
     */
    @Column(name = "user_type")
    private Boolean userType;
    /**
     * 提醒时间
     */
    @Column(name = "remind_date")
    private Date remindDate;
    /**
     * 来源
     */
    @Column(name = "origin")
    private String origin;
    /**
     * 是否阅读
     */
    @Column(name = "is_read")
    private Boolean read;
    /**
     * 最近阅读时间
     */
    @Column(name = "read_date")
    private Date readDate;
    /**
     * 阅读次数
     */
    @Column(name = "read_num")
    private Integer readNum;

    @Override
    public String getTenantCode() {
        return tenantCode;
    }

    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getUserType() {
        return userType;
    }

    public void setUserType(Boolean userType) {
        this.userType = userType;
    }

    public Date getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
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

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

}