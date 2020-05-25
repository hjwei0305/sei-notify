package com.changhong.sei.notify.entity.compose;

import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.Message;

import java.io.Serializable;

/**
 * <strong>实现功能:</strong>
 * <p>消息通告和用户组合</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-15 11:17
 */
public class BulletinCompose implements Serializable {
    private static final long serialVersionUID = 3048139596682418260L;
    /**
     * 通告
     */
    private Bulletin bulletin;

    /**
     * 通告的用户
     */
    private Message message;

    public BulletinCompose() {
    }

    public BulletinCompose(Bulletin bulletin, Message message) {
        this.bulletin = bulletin;
        this.message = message;
    }

    public Bulletin getBulletin() {
        return bulletin;
    }

    public void setBulletin(Bulletin bulletin) {
        this.bulletin = bulletin;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
