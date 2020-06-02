package com.changhong.sei.notify.dto;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能：消息通知类型
 * Bulletin: 适用于在一定范围内公布应当准守或周知的事项
 * Message: 系统内用户之间发送的消息(如同手机短信)。可以针对一个消息进行回复。分为实时消息和非实时消息。
 * Remind: 一般情况下，提醒对于被提醒者来说是被动的。主要是由于外界直接或者间接更新自己相关的信息，对自己产生了影响，自己又不知道的情况下，需要系统主动提醒自己。
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-20 14:27
 */
public enum NotifyType {
    /**
     * 电子邮件
     */
    @Remark("电子邮件")
    EMAIL,
    /**
     * 手机短信
     */
    @Remark("手机短信")
    SMS,
    /**
     * 通告(公告)
     * 适用于在一定范围内公布应当准守或周知的事项
     */
    @Remark("通告")
    SEI_BULLETIN,
    /**
     * 站内信
     * 系统内用户之间发送的消息(如同手机短信)。可以针对一个消息进行回复。分为实时消息和非实时消息。
     */
    @Remark("站内信")
    SEI_MESSAGE,
    /**
     * 提醒
     * 一般情况下，提醒对于被提醒者来说是被动的。主要是由于外界直接或者间接更新自己相关的信息，对自己产生了影响，自己又不知道的情况下，需要系统主动提醒自己。
     */
    @Remark("提醒")
    SEI_REMIND,
    /**
     * 钉钉
     */
    @Remark("钉钉")
    DingTalk,
    /**
     * 微信
     */
    @Remark("微信")
    WeChat,

    /**
     * 微信小程序
     */
    @Remark("微信小程序")
    MiniApp;
}
