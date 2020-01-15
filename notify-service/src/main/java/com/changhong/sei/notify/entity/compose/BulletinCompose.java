package com.changhong.sei.notify.entity.compose;

import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.BulletinUser;

import java.io.Serializable;

/**
 * <strong>实现功能:</strong>
 * <p>消息通告和用户组合</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-15 11:17
 */
public class BulletinCompose implements Serializable {
    /**
     * 通告
     */
    private Bulletin bulletin;

    /**
     * 通告的用户
     */
    private BulletinUser user;

    public BulletinCompose() {
    }

    public BulletinCompose(Bulletin bulletin, BulletinUser user) {
        this.bulletin = bulletin;
        this.user = user;
    }
}
