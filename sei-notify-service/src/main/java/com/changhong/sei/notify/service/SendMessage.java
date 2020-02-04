package com.changhong.sei.notify.service;

import com.changhong.sei.notify.dto.UserNotifyInfo;

import java.io.Serializable;
import java.util.List;

/**
 * <strong>实现功能:</strong>
 * <p>通过消息队列发送的消息实体</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-18 17:11
 */
public class SendMessage implements Serializable {
    /**
     * 主题
     */
    private String subject;
    /**
     * 内容
     */
    private String content;
    /**
     * 发件人（可以为空）
     */
    private UserNotifyInfo sender;
    /**
     * 收件人清单
     */
    private List<UserNotifyInfo> receivers;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserNotifyInfo getSender() {
        return sender;
    }

    public void setSender(UserNotifyInfo sender) {
        this.sender = sender;
    }

    public List<UserNotifyInfo> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<UserNotifyInfo> receivers) {
        this.receivers = receivers;
    }
}
