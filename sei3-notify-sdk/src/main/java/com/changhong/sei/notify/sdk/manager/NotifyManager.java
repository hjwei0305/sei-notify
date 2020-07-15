package com.changhong.sei.notify.sdk.manager;

import com.changhong.sei.notify.dto.EmailMessage;
import com.changhong.sei.notify.sdk.common.HttpClientResult;
import com.changhong.sei.notify.sdk.common.HttpClientUtils;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.SmsMessage;
import com.ecmp.util.JsonUtils;
import com.ecmp.vo.ResponseData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：文档管理器
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-07-11 16:47      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
@SuppressWarnings("rawtypes")
public class NotifyManager implements ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(NotifyManager.class);

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private String getNotifyServiceUrl() {
        String host = context.getEnvironment().getProperty("sei.notify.service.url");
        if (StringUtils.isBlank(host)) {
            throw new IllegalArgumentException("消息服务地址没有配置[sei.notify.service.url]");
        }
        return host;
    }

    private String getBasicServiceUrl() {
        String host = context.getEnvironment().getProperty("sei.basic.service.url");
        if (StringUtils.isBlank(host)) {
            throw new IllegalArgumentException("消息服务地址没有配置[sei.notify.service.url]");
        }
        return host;
    }

    private Map<String, String> getHeaders() {
        Map<String, String> header = new HashMap<>();
        // 设置请求头
//		header.put("Cookie", "");
//		header.put("Connection", "keep-alive");
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json;charset=UTF-8");
//		header.put("Accept-Language", "zh-CN,zh;q=0.9");
//		header.put("Accept-Encoding", "gzip, deflate, br");
//		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");

        return header;
    }

    /**
     * 发送平台消息通知
     * 目前支持: NotifyType.SEI_BULLETIN - 通告, NotifyType.EMAIL - 邮件, NotifyType.SMS - 短信, NotifyType.SEI_REMIND - 站内提醒(默认), NotifyType.MiniApp - 微信小程序
     *
     * @param message 发送的通知消息
     */
    public ResponseData<String> send(NotifyMessage message) {
        if (StringUtils.isBlank(message.getContent()) && StringUtils.isBlank(message.getContentTemplateCode())) {
            return ResponseData.operationFailure("消息内容不能为空.");
        }

        List<NotifyType> notifyTypes = message.getNotifyTypes();
        if (Objects.isNull(notifyTypes)) {
            notifyTypes = new ArrayList<>();
            notifyTypes.add(NotifyType.SEI_REMIND);
        }

        for (NotifyType notifyType : notifyTypes) {
            // 只有通告,可以没有接受人
            if (NotifyType.SEI_BULLETIN != notifyType && CollectionUtils.isEmpty(message.getReceiverIds())) {
                return ResponseData.operationFailure("接收人不能为空.");
            }
        }

        message.setNotifyTypes(notifyTypes);

        ResponseData<String> resultData;
        HttpClientResult result;
        try {
            result = HttpClientUtils.doPostJson(getNotifyServiceUrl() + "/notify/send", JsonUtils.toJson(message));
            resultData = JsonUtils.fromJson(result.getContent(), ResponseData.class);
        } catch (Exception e) {
            LOG.error("发送平台消息通知异常", e);
            resultData = ResponseData.operationFailure("发送平台消息通知异常");
        }

        return resultData;
    }

    /**
     * 发送平台短信通知
     */
    public ResponseData<String> sendSms(SmsMessage message) {
        if (CollectionUtils.isEmpty(message.getPhoneNums())) {
            return ResponseData.operationFailure("接收人不能为空.");
        }

        if (StringUtils.isBlank(message.getContent()) && StringUtils.isBlank(message.getContentTemplateCode())) {
            return ResponseData.operationFailure("消息内容不能为空.");
        }

        ResponseData<String> resultData;
        HttpClientResult result;
        try {
            result = HttpClientUtils.doPostJson(getNotifyServiceUrl() + "/notify/sendSms", JsonUtils.toJson(message));
            resultData = JsonUtils.fromJson(result.getContent(), ResponseData.class);
        } catch (Exception e) {
            LOG.error("发送平台短信通知异常", e);
            resultData = ResponseData.operationFailure("发送平台短信通知异常");
        }

        return resultData;
    }

    /**
     * 发送一封电子邮件
     */
    public ResponseData<String> sendEmail(EmailMessage message) {
        if (CollectionUtils.isEmpty(message.getReceivers())) {
            return ResponseData.operationFailure("接收人不能为空.");
        }

        if (StringUtils.isBlank(message.getContent()) && StringUtils.isBlank(message.getContentTemplateCode())) {
            return ResponseData.operationFailure("消息内容不能为空.");
        }

        ResponseData<String> resultData;
        HttpClientResult result;
        try {
            result = HttpClientUtils.doPostJson(getNotifyServiceUrl() + "/notify/sendEmail", JsonUtils.toJson(message));
            resultData = JsonUtils.fromJson(result.getContent(), ResponseData.class);
        } catch (Exception e) {
            LOG.error("发送平台短信通知异常", e);
            resultData = ResponseData.operationFailure("发送平台短信通知异常");
        }

        return resultData;
    }

    /**
     * 按群组获取接收者
     */
    public ResponseData<List<String>> getReceiverIdsByGroup(String groupCode) {
        Map<String, String> params = new HashMap<>();
        params.put("groupCode", groupCode);

        ResponseData<List<String>> resultData;
        HttpClientResult result;
        try {
            result = HttpClientUtils.doGet(getNotifyServiceUrl() + "/group/getUserIdsByGroup", getHeaders(), params);
            resultData = JsonUtils.fromJson(result.getContent(), ResponseData.class);
        } catch (Exception e) {
            LOG.error("发送平台消息通知异常", e);
            resultData = ResponseData.operationFailure("发送平台消息通知异常");
        }
        return resultData;
    }

    /**
     * 按岗位获取接收者
     */
    public ResponseData<List<String>> getReceiverIdsByPosition(String orgCode, String positionCode) {
        Map<String, String> params = new HashMap<>();
        params.put("orgCode", orgCode);
        params.put("positionCode", positionCode);

        ResponseData<List<String>> resultData;
        HttpClientResult result;
        try {
            result = HttpClientUtils.doGet(getBasicServiceUrl() + "/position/getUserIdsByOrgCodePositionCode", getHeaders(), params);
            resultData = JsonUtils.fromJson(result.getContent(), ResponseData.class);
        } catch (Exception e) {
            LOG.error("发送平台消息通知异常", e);
            resultData = ResponseData.operationFailure("发送平台消息通知异常");
        }
        return resultData;
    }

    /**
     * 按角色获取接收者
     */
    public ResponseData<List<String>> getReceiverIdsByRole(String featureRoleCode) {
        Map<String, String> params = new HashMap<>();
        params.put("featureRoleCodes", featureRoleCode);

        ResponseData<List<String>> resultData;
        HttpClientResult result;
        try {
            result = HttpClientUtils.doGet(getBasicServiceUrl() + "/featureRole/getUserIdsByFeatureRole", getHeaders(), params);
            resultData = JsonUtils.fromJson(result.getContent(), ResponseData.class);
        } catch (Exception e) {
            LOG.error("发送平台消息通知异常", e);
            resultData = ResponseData.operationFailure("发送平台消息通知异常");
        }
        return resultData;
    }
}
