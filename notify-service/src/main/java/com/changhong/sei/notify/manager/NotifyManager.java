package com.changhong.sei.notify.manager;

/**
 * <strong>实现功能:</strong>
 * <p>发送消息的统一接口定义</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-18 16:55
 */
public interface NotifyManager {
    /**
     * 发送消息通知
     * @param message 发送的消息
     */
    void send(SendMessage message);
}
