package com.changhong.sei.notify.entity;

import com.changhong.sei.core.entity.BaseEntity;
import com.chonghong.sei.enums.UserType;

import javax.persistence.*;
import java.util.Date;

/**
 * 实现功能：
 * 通告用户
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-19 17:35
 */
@Entity
@Table(name = "bulletin_user")
public class BulletinUser extends BaseEntity {
    private static final long serialVersionUID = -7194060511283609572L;

    /**
     * 通告id
     */
    @Column(name = "bulletin_id", length = 36)
    private String bulletinId;
    /**
     * 用户id
     */
    @Column(name = "user_id", length = 36)
    private String userId;
    /**
     * 用户类型
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_type", length = 1)
    private UserType userType;
    /**
     * 是否阅读
     */
    @Column(name = "is_read")
    private Boolean read = Boolean.FALSE;
    /**
     * 阅读时间
     */
    @Column(name = "read_date")
    private Date readDate;

    public String getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(String bulletinId) {
        this.bulletinId = bulletinId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }
}
