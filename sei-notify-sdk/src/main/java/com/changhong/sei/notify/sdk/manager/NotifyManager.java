package com.changhong.sei.notify.sdk.manager;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.EmailMessage;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.SmsMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ParameterizedTypeReference;

import java.util.*;

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

    private String getNotifyServiceUrl() {
        return context.getEnvironment().getProperty("sei.notify.service.url", "http://10.4.208.86:20001/sei-notify");
    }

    private String getBasicServiceUrl() {
        return context.getEnvironment().getProperty("sei.basic.service.url", "http://10.4.208.86:20004/sei-basic");
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
        message.setNotifyTypes(notifyTypes);

        ResultData<String> resultData = apiTemplate.postByUrl(getNotifyServiceUrl() + "/notify/send",
                new ParameterizedTypeReference<ResultData<String>>() {
                }, message);
        return resultData;
    }

    /**
     * 发送平台短信通知
     */
    public ResultData<String> sendSms(SmsMessage message) {
        if (CollectionUtils.isEmpty(message.getPhoneNums())) {
            return ResultData.fail("接收人不能为空.");
        }

        if (StringUtils.isBlank(message.getContent()) && StringUtils.isBlank(message.getContentTemplateCode())) {
            return ResultData.fail("消息内容不能为空.");
        }

        ResultData<String> resultData = apiTemplate.postByUrl(getNotifyServiceUrl() + "/notify/sendSms",
                new ParameterizedTypeReference<ResultData<String>>() {
                }, message);
        return resultData;
    }

    /**
     * 发送一封电子邮件
     */
    public ResultData<String> sendEmail(EmailMessage message) {
        if (CollectionUtils.isEmpty(message.getReceivers())) {
            return ResultData.fail("接收人不能为空.");
        }

        if (StringUtils.isBlank(message.getContent()) && StringUtils.isBlank(message.getContentTemplateCode())) {
            return ResultData.fail("消息内容不能为空.");
        }

        ResultData<String> resultData = apiTemplate.postByUrl(getNotifyServiceUrl() + "/notify/sendEmail",
                new ParameterizedTypeReference<ResultData<String>>() {
                }, message);
        return resultData;
    }

    /**
     * 按群组获取接收者
     */
    public ResultData<List<String>> getReceiverIdsByGroup(String groupCode) {
        Map<String, String> params = new HashMap<>();
        params.put("groupCode", groupCode);
        return apiTemplate.getByUrl(getNotifyServiceUrl() + "/group/getUserIdsByGroup", new ParameterizedTypeReference<ResultData<List<String>>>() {
        }, params);
    }

    /**
     * 按岗位获取接收者
     */
    public ResultData<List<String>> getReceiverIdsByPosition(String orgCode, String positionCode) {
        Map<String, String> params = new HashMap<>();
        params.put("orgCode", orgCode);
        params.put("positionCodes", positionCode);
        return apiTemplate.getByUrl(getBasicServiceUrl() + "/position/getUserIdsByOrgCodePositionCode", new ParameterizedTypeReference<ResultData<List<String>>>() {
        }, params);
    }

    /**
     * 按角色获取接收者
     */
    public ResultData<List<String>> getReceiverIdsByRole(String featureRoleCode) {
        Map<String, String> params = new HashMap<>();
        params.put("featureRoleCodes", featureRoleCode);
        return apiTemplate.getByUrl(getBasicServiceUrl() + "/featureRole/getUserIdsByFeatureRole", new ParameterizedTypeReference<ResultData<List<String>>>() {
        }, params);
    }
}
