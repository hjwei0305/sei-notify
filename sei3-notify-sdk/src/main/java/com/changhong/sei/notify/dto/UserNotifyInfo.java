package com.changhong.sei.notify.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：用户的消息通知信息
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-06-15 16:50      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
public class UserNotifyInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 移动电话
     */
    private String mobile;

    /**
     * 微信openId
     */
    private Set<String> weChatOpenId;

    /**
     * 微信小程序openId
     */
    private Set<String> miniProgramOpenId;

    public String getUserId() {
        return userId;
    }

    public UserNotifyInfo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public UserNotifyInfo setUserAccount(String userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserNotifyInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserNotifyInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public UserNotifyInfo setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public static UserNotifyInfo builder() {
        return new UserNotifyInfo();
    }

    public Set<String> getWeChatOpenId() {
        return weChatOpenId;
    }

    public void setWeChatOpenId(Set<String> weChatOpenId) {
        this.weChatOpenId = weChatOpenId;
    }

    public Set<String> getMiniProgramOpenId() {
        return miniProgramOpenId;
    }

    public void setMiniProgramOpenId(Set<String> miniProgramOpenId) {
        this.miniProgramOpenId = miniProgramOpenId;
    }
}
