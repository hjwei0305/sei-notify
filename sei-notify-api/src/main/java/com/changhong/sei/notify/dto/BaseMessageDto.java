package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-23 17:09
 */
public class BaseMessageDto extends BaseEntityDto implements Serializable {

    private static final long serialVersionUID = 132979558246200348L;
    /**
     * 类别
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    protected NotifyType category;
    /**
     * 消息主题
     */
    protected String subject;
    /**
     * 内容id
     */
    protected String contentId;
    /**
     * 内容
     */
    protected String content;
    /**
     * 优先级
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    protected Priority priority;

    /**
     * 发布时间
     */
    @JsonFormat(timezone = DateUtils.DEFAULT_TIMEZONE, pattern = DateUtils.DEFAULT_TIME_FORMAT)
    private LocalDateTime publishDate;
    protected Boolean read = Boolean.FALSE;

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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public BaseMessageDto setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
