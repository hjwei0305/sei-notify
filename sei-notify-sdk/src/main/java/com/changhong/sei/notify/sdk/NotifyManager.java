package com.changhong.sei.notify.sdk;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.NotifyType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-04-20 22:42
 */
public class NotifyManager implements ApplicationContextAware {

    private final ApiTemplate apiTemplate;

    public NotifyManager(ApiTemplate apiTemplate) {
        this.apiTemplate = apiTemplate;
    }

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private String getServiceUrl() {
        return context.getEnvironment().getProperty("sei.notify.server-url", "http://10.4.208.86:20001/sei-notify");
    }

    /**
     * 发送平台消息通知
     * 目前支持: NotifyType.EMAIL - 邮件, NotifyType.SMS - 短信, NotifyType.SEI_REMIND - 站内提醒(默认)
     *
     * @param message 发送的通知消息
     */
    public ResultData<String> send(NotifyMessage message) {
        if (CollectionUtils.isEmpty(message.getReceiverIds())) {
            return ResultData.fail("接收人不能为空.");
        }

        if (StringUtils.isBlank(message.getContent()) && StringUtils.isBlank(message.getContentTemplateCode())) {
            return ResultData.fail("消息内容不能为空.");
        }

        List<NotifyType> notifyTypes = message.getNotifyTypes();
        if (Objects.isNull(notifyTypes)) {
            notifyTypes = new ArrayList<>();
            notifyTypes.add(NotifyType.SEI_REMIND);
        }

        ResultData<String> resultData = apiTemplate.postByUrl(getServiceUrl() + "/notify/send",
                new ParameterizedTypeReference<ResultData<String>>() {
                }, message);
        return resultData;
    }

}
