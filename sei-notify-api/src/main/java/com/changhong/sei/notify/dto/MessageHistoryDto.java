package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Set;

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
    @ApiModelProperty(value = "消息类型")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private NotifyType category;
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
     * 目标类型
     */
    @ApiModelProperty(value = "接收类型")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private TargetType targetType = TargetType.PERSONAL;
    /**
     * 目标对象id
     */
    @ApiModelProperty(value = "接收人")
    private String targetValue;
    /**
     * 目标对象名称
     */
    @ApiModelProperty(value = "接收人名称")
    private String targetName;
    /**
     * 是否发布
     */
    @ApiModelProperty(value = "发送状态")
    private Boolean sendStatus;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendDate;
    /**
     * 删除人id
     */
    @ApiModelProperty(value = "发送日志")
    private String sendLog;
    /**
     * 业务属性
     */
    @ApiModelProperty(value = "业务属性")
    private String bizValue;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    protected String content;
    /**
     * 附件id
     */
    @ApiModelProperty(value = "附件id")
    private Set<String> docIds;

    public NotifyType getCategory() {
        return category;
    }

    public void setCategory(NotifyType category) {
        this.category = category;
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

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
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

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<String> getDocIds() {
        return docIds;
    }

    public void setDocIds(Set<String> docIds) {
        this.docIds = docIds;
    }
}