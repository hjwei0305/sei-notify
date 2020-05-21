package com.changhong.sei.notify.manager.demo;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.notify.manager.NotifyManager;
import com.changhong.sei.notify.dto.SendMessage;
import org.springframework.stereotype.Component;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-22 00:06
 */
@Component(HelloManager.HELLO_MQ_KEY)
public class HelloManager implements NotifyManager {
    public static final String HELLO_MQ_KEY ="hello";

    /**
     * 发送消息通知
     *
     * @param message 发送的消息
     */
    @Override
    public ResultData<String> send(SendMessage message) {
        LogUtil.bizLog("执行业务逻辑：你好！" + message);
        return ResultData.success("OK");
    }
}
