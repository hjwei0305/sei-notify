package com.changhong.sei.notify.dto;

import com.changhong.sei.enums.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * 提醒信息(Remind)DTO类
 *
 * @author sei
 * @since 2020-05-22 18:09:43
 */
@ApiModel(description = "提醒用户")
public class RemindUser implements Serializable {
    private static final long serialVersionUID = 390597983994017121L;

    /**
     * 用户id
     */
    @NotBlank
    @ApiModelProperty(value = "用户id")
    private String userId;
    /**
     * 用户账号
     */
    @NotBlank
    @ApiModelProperty(value = "用户账号")
    private String userAccount;
    /**
     * 用户名称
     */
    @NotBlank
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private UserType userType;

    public String getUserId() {
        return userId;
    }

    public RemindUser setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public RemindUser setUserAccount(String userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public RemindUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public RemindUser setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public static RemindUser builder() {
        return new RemindUser();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RemindUser that = (RemindUser) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}