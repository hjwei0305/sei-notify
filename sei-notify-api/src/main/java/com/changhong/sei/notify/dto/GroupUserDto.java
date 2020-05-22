package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

/**
 * 群组(GroupUser)DTO类
 *
 * @author sei
 * @since 2020-05-22 11:04:28
 */
@ApiModel(description = "群组DTO")
public class GroupUserDto extends BaseEntityDto {
    private static final long serialVersionUID = 755755634778478503L;
    /**
     * 群组id
     */
    @NotBlank
    @ApiModelProperty(value = "群组id")
    private String groupId;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

}