package com.changhong.sei.notify.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * <strong>实现功能:</strong>
 * <p>通过消息队列发送的消息实体</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-18 17:11
 */
public class SendMessage implements Serializable {
    private static final long serialVersionUID = -7977999907838578550L;
    /**
     * 主题
     */
    private String subject;
    /**
     * 内容
     */
    private String content;
    /**
     * 收件人清单
     */
    private List<UserNotifyInfo> receivers;
    /**
     * 发件人（可以为空）
     */
    private UserNotifyInfo sender;
    /**
     * 附件id（可以为空）
     */
    private Set<String> docIds;

    public String getSubject() {
        return subject;
    }

    public SendMessage setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SendMessage setContent(String content) {
        this.content = content;
        return this;
    }

    public UserNotifyInfo getSender() {
        return sender;
    }

    public SendMessage setSender(UserNotifyInfo sender) {
        this.sender = sender;
        return this;
    }

    public List<UserNotifyInfo> getReceivers() {
        return receivers;
    }

    public SendMessage setReceivers(List<UserNotifyInfo> receivers) {
        this.receivers = receivers;
        return this;
    }

    public Set<String> getDocIds() {
        return docIds;
    }

    public SendMessage setDocIds(Set<String> docIds) {
        this.docIds = docIds;
        return this;
    }

    public static SendMessage builder() {
        return new SendMessage();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SendMessage.class.getSimpleName() + "[", "]")
                .add("subject='" + subject + "'")
                .add("content='" + content + "'")
                .add("sender=" + sender)
                .add("docIds=" + docIds)
                .add("receivers=" + receivers)
                .toString();
    }
}
