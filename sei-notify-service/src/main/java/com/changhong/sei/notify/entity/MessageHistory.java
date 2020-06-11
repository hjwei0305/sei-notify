package com.changhong.sei.notify.entity;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.TargetType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 消息历史(MessageHistory)实体类
 *
 * @author sei
 * @since 2020-06-11 14:36:17
 */
@Entity
@Table(name = "message_history")
@DynamicInsert
@DynamicUpdate
public class MessageHistory extends BaseEntity implements Serializable {
private static final long serialVersionUID = -26932064614327653L;
    /**
     * 消息通知类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "notify_type")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private NotifyType category;
    /**
     * 主题
     */
    @Column(name = "subject")
    private String subject;
    /**
     * 内容id
     */
    @Column(name = "content_id")
    private String contentId;
    /**
     * 目标类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private TargetType targetType = TargetType.PERSONAL;
    /**
     * 目标对象Value
     * targetType如果是TargetType.PERSONAL, 则为userId
     */
    @Column(name = "target_value")
    private String targetValue;
    /**
     * 目标对象名称
     */
    @Column(name = "target_name")
    private String targetName;
    /**
     * 是否发布
     */
    @Column(name = "send_status")
    private Boolean sendStatus;
    /**
     * 发布时间
     */
    @Column(name = "send_date")
    private LocalDateTime sendDate;
    /**
     * 消息日志
     */
    @Column(name = "send_log")
    private String sendLog;
    /**
     * 业务属性
     */
    @Column(name = "biz_value")
    private String bizValue;

    /**
     * 内容
     */
    @Transient
    protected String content;
    /**
     * 附件id
     */
    @Transient
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