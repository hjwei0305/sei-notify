package com.changhong.sei.notify.entity;

import com.changhong.sei.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

/**
 * <strong>实现功能:</strong>
 * <p>小程序推送次数</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 10:40
 */
@Entity
@Table(name = "user_miniapp_push_time")
@DynamicInsert
@DynamicUpdate
public class UserMiniAppPushTime extends BaseEntity {

    /**
     * 用户Id
     */
    @Column(name = "userId", length = 36, nullable = false)
    private String userId;
    /**
     * 用户
     */
    @Column(name = "user_account")
    private String userAccount;
    /**
     * 用户
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 微信小程序openId
     */
    @Column(name = "mini_program_openId",unique = true)
    private String miniProgramOpenId;

    /**
     * 剩余推送次数
     */
    @Column(name = "push_time")
    private Integer pushTime = 0;

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

    public String getMiniProgramOpenId() {
        return miniProgramOpenId;
    }

    public void setMiniProgramOpenId(String miniProgramOpenId) {
        this.miniProgramOpenId = miniProgramOpenId;
    }

    public Integer getPushTime() {
        return pushTime;
    }

    public void setPushTime(Integer pushTime) {
        this.pushTime = pushTime;
    }
}
