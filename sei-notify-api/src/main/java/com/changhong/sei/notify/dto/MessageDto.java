package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-23 17:09
 */
@ApiModel(description = "消息传输对象")
public class MessageDto extends BaseEntityDto implements Serializable {

    private static final long serialVersionUID = 132979558246200348L;
    /**
     * 类别
     */
    @ApiModelProperty(notes = "消息类别", required = true)
    @JsonSerialize(using = EnumJsonSerializer.class)
    protected NotifyType category;
    /**
     * 消息主题
     */
    @ApiModelProperty(notes = "消息主题", required = true)
    protected String subject;
    /**
     * 内容id
     */
    @ApiModelProperty(notes = "消息内容id")
    protected String contentId;
    /**
     * 内容
     */
    @ApiModelProperty(notes = "消息内容", required = true)
    protected String content;
    /**
     * 目标对象Value
     */
    @ApiModelProperty(notes = "消息发送目标对象id.当为targetType = SYSTEM时,为SYSTEM")
    private String targetValue;

    /**
     * 目标对象name
     */
    @ApiModelProperty(notes = "消息发送目标对象名称.当为targetType = SYSTEM时,为SYSTEM")
    private String targetName;

    /**
     * 目标类型
     */
    @ApiModelProperty(notes = "消息发送目标类型", required = true)
    @JsonSerialize(using = EnumJsonSerializer.class)
    private TargetType targetType;
    /**
     * 优先级
     */
    @ApiModelProperty(notes = "优先级")
    @JsonSerialize(using = EnumJsonSerializer.class)
    protected Priority priority = Priority.General;
    /**
     * 是否发布
     */
    @ApiModelProperty(notes = "消息是否已发布")
    private Boolean publish = Boolean.FALSE;
    /**
     * 发布时间时间
     */
    @ApiModelProperty(notes = "消息发布时间")
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_TIME_FORMAT)
    protected LocalDateTime publishDate;
    /**
     * 发布人
     */
    @ApiModelProperty(notes = "消息发布人账号")
    private String publishUserAccount;
    /**
     * 发布人
     */
    @ApiModelProperty(notes = "消息发布人名称")
    private String publishUserName;

    protected Boolean read = Boolean.FALSE;
    /**
     * 最近阅读时间
     */
    @ApiModelProperty(notes = "消息发布时间")
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_TIME_FORMAT)
    private LocalDateTime readDate;
    /**
     * 业务属性
     */
    @ApiModelProperty(notes = "发布消息关联的业务属性值")
    protected String bizValue;
    /**
     * 阅读次数
     */
    @ApiModelProperty(notes = "消息阅读次数")
    private Integer readNum = 0;
    /**
     * 附件id
     */
    @ApiModelProperty(notes = "消息关联的附件id清单(EDM集成)")
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishUserAccount() {
        return publishUserAccount;
    }

    public void setPublishUserAccount(String publishUserAccount) {
        this.publishUserAccount = publishUserAccount;
    }

    public String getPublishUserName() {
        return publishUserName;
    }

    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
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

    public Set<String> getDocIds() {
        return docIds;
    }

    public void setDocIds(Set<String> docIds) {
        this.docIds = docIds;
    }

    public String getBizValue() {
        return bizValue;
    }

    public void setBizValue(String bizValue) {
        this.bizValue = bizValue;
    }
}
