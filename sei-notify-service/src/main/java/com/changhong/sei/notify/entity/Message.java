package com.changhong.sei.notify.entity;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.core.entity.BaseEntity;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.Priority;
import com.changhong.sei.notify.dto.TargetType;
import com.changhong.sei.notify.entity.cust.MessageCust;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 实现功能：消息基类
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-20 14:22
 */
@Entity
@Table(name = "message")
@DynamicInsert
@DynamicUpdate
public class Message extends MessageCust {
    private static final long serialVersionUID = 8454048674835991508L;
    public static final String FIELD_CATEGORY = "category";

    /**
     * 消息通知类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "notify_type")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private NotifyType category;
    /**
     * 消息主题
     */
    @Column(name = "subject", nullable = false)
    protected String subject;
    /**
     * 内容id
     */
    @Column(name = "content_id", length = 36)
    protected String contentId;
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
     * 目标对象name
     */
    @Column(name = "target_name")
    private String targetName;
    /**
     * 优先级
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority_")
    @JsonSerialize(using = EnumJsonSerializer.class)
    protected Priority priority = Priority.General;

    /**
     * 是否发布
     */
    @Column(name = "is_publish")
    private Boolean publish = Boolean.FALSE;
    /**
     * 发布时间时间
     */
    @Column(name = "publish_date")
    protected LocalDateTime publishDate;
    /**
     * 发布人
     */
    @Column(name = "publish_user_account")
    private String publishUserAccount;
    /**
     * 发布人
     */
    @Column(name = "publish_user_name")
    private String publishUserName;
    /**
     * 是否有效
     */
    @Column(name = "is_effective")
    protected Boolean effective = Boolean.TRUE;
    /**
     * 是否删除
     */
    @Column(name = "is_del")
    protected Boolean del = Boolean.FALSE;
    /**
     * 删除时间
     */
    @Column(name = "del_date")
    protected LocalDateTime delDate;
    /**
     * 删除人id
     */
    @Column(name = "del_user_account")
    protected String delUserAccount;
    /**
     * 删除人id
     */
    @Column(name = "del_user_name")
    protected String delUserName;
    /**
     * 业务属性
     */
    @Column(name = "biz_value")
    protected String bizValue;

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

    public void setCategory(NotifyType notifyType) {
        this.category = notifyType;
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

    public Boolean getEffective() {
        return effective;
    }

    public void setEffective(Boolean effective) {
        this.effective = effective;
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public LocalDateTime getDelDate() {
        return delDate;
    }

    public void setDelDate(LocalDateTime delDate) {
        this.delDate = delDate;
    }

    public String getDelUserAccount() {
        return delUserAccount;
    }

    public void setDelUserAccount(String delUserAccount) {
        this.delUserAccount = delUserAccount;
    }

    public String getDelUserName() {
        return delUserName;
    }

    public void setDelUserName(String delUserName) {
        this.delUserName = delUserName;
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
