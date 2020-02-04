package com.changhong.sei.notify.service;

import org.springframework.stereotype.Service;

/**
 * <strong>实现功能:</strong>
 * <p>SMS短信消息的处理类</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-18 20:23
 */
@Service("SMS")
public class SmsService implements NotifyService {
    /**
     * 发送消息通知
     *
     * @param message 发送的消息
     */
    @Override
    public void send(SendMessage message) {
        System.out.println("模拟发送短信："+message.getContent());
    }
}
