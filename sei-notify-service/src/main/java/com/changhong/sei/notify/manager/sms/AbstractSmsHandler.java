package com.changhong.sei.notify.manager.sms;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.config.SmsProperties;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2022-03-12 16:40
 */
public interface AbstractSmsHandler {
    /**
     * 发送消息通知
     *
     * @param properties 配置对象
     * @param phoneNums  发送的手机号
     * @param content    发送的消息
     */
    ResultData<Void> send(SmsProperties properties, String[] phoneNums, String content) throws Exception;
}
