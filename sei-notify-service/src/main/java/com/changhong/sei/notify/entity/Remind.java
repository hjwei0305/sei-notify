package com.changhong.sei.notify.entity;

import com.changhong.sei.core.entity.ITenant;
import com.changhong.sei.enums.UserType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Remind extends BaseMessage implements ITenant, Serializable {
    private static final long serialVersionUID = -32108204849583924L;
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_READ = "read";
    /**
     * 租户代码
     */
    @Column(name = "tenant_code")
    private String tenantCode;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;
    /**
     * 创建提醒时间
     */
    @Column(name = "remind_date")
    private LocalDateTime remindDate;
    /**
     * 来源
     */
    @Column(name = "origin")
    private String origin;
    /**
     * 是否阅读
     */
    @Column(name = "is_read")
    private Boolean read = Boolean.FALSE;
    /**
     * 最近阅读时间
     */
    @Column(name = "read_date")
    private LocalDateTime readDate;
    /**
     * 阅读次数
     */
    @Column(name = "read_num")
    private Integer readNum = 0;

    @Override
    public String getTenantCode() {
        return tenantCode;
    }

    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public LocalDateTime getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(LocalDateTime remindDate) {
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

    public LocalDateTime getReadDate() {
        return readDate;
    }

    public void setReadDate(LocalDateTime readDate) {
        this.readDate = readDate;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

}