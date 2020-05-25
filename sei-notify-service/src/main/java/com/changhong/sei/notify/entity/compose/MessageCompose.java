package com.changhong.sei.notify.entity.compose;

import com.changhong.sei.notify.entity.Message;
import com.changhong.sei.notify.entity.MessageUser;

import java.io.Serializable;

/**
 * <strong>实现功能:</strong>
 * <p>消息通告和用户组合</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-15 11:17
 */
public class MessageCompose implements Serializable {
    /**
     * 通告
     */
    private Message message;

    /**
     * 通告的用户
     */
    private MessageUser user;

    /**
     * 通告的内容
     */
    private String content;

    public MessageCompose() {
    }

    public MessageCompose(Message message, MessageUser user) {
        this.message = message;
        this.user = user;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public MessageUser getUser() {
        return user;
    }

    public void setUser(MessageUser user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
