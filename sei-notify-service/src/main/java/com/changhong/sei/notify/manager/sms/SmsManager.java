package com.changhong.sei.notify.manager.sms;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.notify.config.SmsProperties;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.SendMessage;
import com.changhong.sei.notify.dto.TargetType;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.entity.MessageHistory;
import com.changhong.sei.notify.manager.NotifyManager;
import com.changhong.sei.notify.service.MessageHistoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2022-03-11 14:25
 */
public class SmsManager implements NotifyManager {
    private static final Logger LOG = LoggerFactory.getLogger(SmsManager.class);

    private final SmsProperties properties;
    private static final String DEFAULT_HANDLER_KEY = "default";
    private static final String MATRIX_HANDLER_KEY = "matrix";
    private static final String ALI_HANDLER_KEY = "aliyun";
    private static final Map<String, AbstractSmsHandler> SMS_HANDLER_MAP;

    @Autowired
    private MessageHistoryService historyService;

    static {
        SMS_HANDLER_MAP = new HashMap<>();
        //MatrixSmsHandler matrixSmsHandler = new MatrixSmsHandler();
        //新宝短信平台
        SMS_HANDLER_MAP.put(DEFAULT_HANDLER_KEY, new XbSmsHandler());
        // 软服Matrix短信网关
       // SMS_HANDLER_MAP.put(MATRIX_HANDLER_KEY, matrixSmsHandler);
        // 阿里云短消息服务
      //  SMS_HANDLER_MAP.put(ALI_HANDLER_KEY, new AliSmsHandler());

    }

    public SmsManager(SmsProperties properties) {
        this.properties = properties;
    }

    /**
     * 发送消息通知
     *
     * @param message 发送的消息
     */
    @Override
    public ResultData<String> send(SendMessage message) {
       // boolean success = properties.getEnable();
        boolean success = true;
        LogUtil.bizLog("短信开启："+properties.getEnable());
        String log = "success";
        String content = message.getContent();

        List<MessageHistory> histories = new ArrayList<>();
        Set<String> phoneNumSet = new HashSet<>();
        MessageHistory history;
        for (UserNotifyInfo info : message.getReceivers()) {
            phoneNumSet.add(info.getMobile());

            history = new MessageHistory();
            history.setCategory(NotifyType.SMS);
            history.setSubject(StringUtils.isNotBlank(message.getSubject()) ? message.getSubject() : StringUtils.left(message.getContent(), 100));
            history.setTargetType(TargetType.PERSONAL);
            history.setTargetValue(info.getMobile());
            history.setTargetName(StringUtils.isNotBlank(info.getUserName()) ? info.getUserName() : info.getMobile());
            histories.add(history);
        }

        try {
            if (success) {
                String[] phoneNums = phoneNumSet.toArray(new String[0]);

                //String provider = properties.getProvider();
                //if (StringUtils.isBlank(provider)) {
                //    provider = DEFAULT_HANDLER_KEY;
                //}

                AbstractSmsHandler smsHandler = SMS_HANDLER_MAP.get(DEFAULT_HANDLER_KEY);
                ResultData<Void> resultData = smsHandler.send(properties, phoneNums, message.getContent());
                if (resultData.failed()) {
                    success = Boolean.FALSE;
                    log = resultData.getMessage();
                }
            } else {
                // 未配置开启手机短信服务
                log = ContextUtil.getMessage("00018");
            }
        } catch (Exception e) {
            success = Boolean.FALSE;
            // 手机短信发送失败
            log = ContextUtil.getMessage("00019");
            LOG.error("发送短信异常", e);
        } finally {
            try {
                historyService.recordHistory(histories, content, success, log, null);
            } catch (Exception e) {
                LogUtil.error("记录消息历史异常", e);
            }
        }
        if (success) {
            return ResultData.success(log);
        } else {
            return ResultData.fail(log);
        }
    }

    /**
     * 注册短信服务处理器
     *
     * @param key        短信服务提供商标识.
     *                   注意不能使用: {@link SmsManager#DEFAULT_HANDLER_KEY}, {@link SmsManager#MATRIX_HANDLER_KEY}, {@link SmsManager#ALI_HANDLER_KEY}
     * @param smsHandler 短信服务处理器
     */
    public void register(String key, AbstractSmsHandler smsHandler) {
        SMS_HANDLER_MAP.put(key, smsHandler);
    }
}
