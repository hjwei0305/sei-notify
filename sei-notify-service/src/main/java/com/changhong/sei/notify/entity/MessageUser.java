package com.changhong.sei.notify.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.enums.UserType;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 实现功能：
 * 用户消息
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-19 17:35
 */
@Entity
@Table(name = "message_user")
public class MessageUser extends BaseEntity {
    private static final long serialVersionUID = -7194060511283609572L;
    public static final String FIELD_READ = "read";

    /**
     * 消息id
     */
    @Column(name = "msg_id", length = 36)
    private String msgId;
    /**
     * 用户id
     */
    @Column(name = "user_id", length = 36)
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
     * 用户类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;
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

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
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
