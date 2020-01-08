package com.changhong.sei.notify.manager;

import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.mq.MqConsumer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.changhong.sei.notify.manager.HelloManager.MQ_KEY;

/**
 * <strong>实现功能:</strong>
 * <p>消息通知的队列消费者</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-08 14:31
 */
@Component
public class NotifyConsumer extends MqConsumer {

    /**
     * 收到的监听消息后的业务处理
     *
     * @param message 队列消息
     */
    @Override
    public void process(String message) {
        LogUtil.bizLog("执行收到的监听消息后的业务处理。message="+message);
        if (StringUtils.isNotBlank(getKey())){
            if (getKey().equals(MQ_KEY)){
                LogUtil.bizLog("执行业务逻辑：你好！"+message);
            }
        }
    }
}
