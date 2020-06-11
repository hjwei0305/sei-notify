package com.changhong.sei.notify.dto;

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
 * 消息历史(MessageHistory)DTO类
 *
 * @author sei
 * @since 2020-06-11 14:36:34
 */
@ApiModel(description = "消息历史DTO")
public class MessageHistoryDto extends BaseEntityDto {
private static final long serialVersionUID = 179365424315447841L;
    /**
     * 通知类型
     */
    @ApiModelProperty(value = "通知类型")
    private String notifyType;
    /**
     * 主题
     */
    @ApiModelProperty(value = "主题")
    private String subject;
    /**
     * 内容id
     */
    @ApiModelProperty(value = "内容id")
    private String contentId;
    /**
     * 优先级
     */
    @ApiModelProperty(value = "优先级")
    private String priority_;
    /**
     * 目标类型
     */
    @ApiModelProperty(value = "目标类型")
    private String targetType;
    /**
     * 目标对象id
     */
    @ApiModelProperty(value = "目标对象id")
    private String targetValue;
    /**
     * 目标对象名称
     */
    @ApiModelProperty(value = "目标对象名称")
    private String targetName;
    /**
     * 是否发布
     */
    @ApiModelProperty(value = "是否发布")
    private Boolean sendStatus;
    /**
     * 发布人
     */
    @ApiModelProperty(value = "发布人")
    private String sendUserAccount;
    /**
     * 发布人
     */
    @ApiModelProperty(value = "发布人")
    private String sendUserName;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private Date sendDate;
    /**
     * 删除人id
     */
    @ApiModelProperty(value = "删除人id")
    private String sendLog;
    /**
     * 业务属性
     */
    @ApiModelProperty(value = "业务属性")
    private String bizValue;

        
    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }
        
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
        
    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
        
    public String getPriority_() {
        return priority_;
    }

    public void setPriority_(String priority_) {
        this.priority_ = priority_;
    }
        
    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
        
    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }
        
    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
        
    public Boolean getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Boolean sendStatus) {
        this.sendStatus = sendStatus;
    }
        
    public String getSendUserAccount() {
        return sendUserAccount;
    }

    public void setSendUserAccount(String sendUserAccount) {
        this.sendUserAccount = sendUserAccount;
    }
        
    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }
        
    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
        
    public String getSendLog() {
        return sendLog;
    }

    public void setSendLog(String sendLog) {
        this.sendLog = sendLog;
    }
        
    public String getBizValue() {
        return bizValue;
    }

    public void setBizValue(String bizValue) {
        this.bizValue = bizValue;
    }

}