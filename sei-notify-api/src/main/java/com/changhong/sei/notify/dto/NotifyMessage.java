package com.changhong.sei.notify.dto;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：平台发送的消息通知
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-06-15 15:18      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
public class NotifyMessage implements Serializable, MessageContent {
    private static final long serialVersionUID = 1L;
    /**
     * 消息通知方式
     */
    private List<NotifyType> notifyTypes;
    /**
     * 消息主题
     */
    private String subject;
    /**
     * 内容
     */
    private String content;
    /**
     * 发送用户Id
     */
    private String senderId;
    /**
     * 收件用户Id清单
     */
    private List<String> receiverIds;
    /**
     * 内容模板代码
     */
    private String contentTemplateCode;
    /**
     * 内容模板参数
     */
    private Map<String, Object> contentTemplateParams;
    /**
     * 附件id（可以为空）
     */
    private Set<String> docIds;
    /**
     * 可以发送给发件人
     */
    private boolean canToSender = Boolean.FALSE;

    public NotifyMessage() {
    }

    public NotifyMessage(String subject) {
        this.subject = subject;
    }
    public NotifyMessage(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public List<String> getReceiverIds() {
        return receiverIds;
    }

    public void setReceiverIds(List<String> receiverIds) {
        this.receiverIds = receiverIds;
    }

    @Override
    public String getContentTemplateCode() {
        return contentTemplateCode;
    }

    public void setContentTemplateCode(String contentTemplateCode) {
        this.contentTemplateCode = contentTemplateCode;
    }

    @Override
    public Map<String, Object> getContentTemplateParams() {
        return contentTemplateParams;
    }

    public void setContentTemplateParams(Map<String, Object> contentTemplateParams) {
        this.contentTemplateParams = contentTemplateParams;
    }

    public List<NotifyType> getNotifyTypes() {
        return notifyTypes;
    }

    public void setNotifyTypes(List<NotifyType> notifyTypes) {
        this.notifyTypes = notifyTypes;
    }

    public Set<String> getDocIds() {
        return docIds;
    }

    public void setDocIds(Set<String> docIds) {
        this.docIds = docIds;
    }

    public boolean isCanToSender() {
        return canToSender;
    }

    public void setCanToSender(boolean canToSender) {
        this.canToSender = canToSender;
    }

    public List<NotifyType> addNotifyType(NotifyType notifyType) {
        if (Objects.isNull(notifyTypes)) {
            notifyTypes = new ArrayList<>();
        }
        if (Objects.nonNull(notifyType)) {
            notifyTypes.add(notifyType);
        }
        return notifyTypes;
    }

    public List<String> addReceiverId(String userId) {
        if (Objects.isNull(receiverIds)) {
            receiverIds = new ArrayList<>();
        }
        if (StringUtils.isNotBlank(userId)) {
            receiverIds.add(userId);
        }
        return receiverIds;
    }
}
