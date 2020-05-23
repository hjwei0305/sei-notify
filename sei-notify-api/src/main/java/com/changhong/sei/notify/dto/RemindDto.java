package com.changhong.sei.notify.dto;

import java.time.LocalDateTime;
import java.util.Date;
import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

/**
 * 提醒信息(Remind)DTO类
 *
 * @author sei
 * @since 2020-05-22 18:09:43
 */
@ApiModel(description = "提醒信息DTO")
public class RemindDto extends BaseEntityDto {
private static final long serialVersionUID = 390597983994017121L;
    /**
     * 内容id
     */
    @ApiModelProperty(value = "内容id")
    private String contentId;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;
    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    private String userAccount;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private Boolean userType;
    /**
     * 提醒时间
     */
    @ApiModelProperty(value = "提醒时间")
    private LocalDateTime remindDate;
    /**
     * 来源
     */
    @ApiModelProperty(value = "来源")
    private String origin;
    /**
     * 是否阅读
     */
    @ApiModelProperty(value = "是否阅读")
    private Boolean read;
    /**
     * 最近阅读时间
     */
    @ApiModelProperty(value = "最近阅读时间")
    private LocalDateTime readDate;
    /**
     * 阅读次数
     */
    @ApiModelProperty(value = "阅读次数")
    private Integer readNum;
        
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