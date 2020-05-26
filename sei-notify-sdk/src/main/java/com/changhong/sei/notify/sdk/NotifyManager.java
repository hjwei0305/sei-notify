package com.changhong.sei.notify.sdk;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.NotifyMessage;
import com.changhong.sei.notify.dto.NotifyType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;

import java.util.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-04-20 22:42
 */
public class NotifyManager {

    private final ApiTemplate apiTemplate;

    public NotifyManager(ApiTemplate apiTemplate) {
        this.apiTemplate = apiTemplate;
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

        ResultData<String> resultData = apiTemplate.postByAppModuleCode("sei-notify", "/notify/send",
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
        return apiTemplate.getByAppModuleCode("sei-notify", "/position/getUserIdsByGroup", new ParameterizedTypeReference<ResultData<List<String>>>() {
        }, params);
    }

    /**
     * 按岗位获取接收者
     */
    public ResultData<List<String>> getReceiverIdsByPosition(String positionCode) {
        Map<String, String> params = new HashMap<>();
        params.put("positionCodes", positionCode);
        return apiTemplate.getByAppModuleCode("sei-basic", "/position/getUserIdsByPositionCode", new ParameterizedTypeReference<ResultData<List<String>>>() {
        }, params);
    }

    /**
     * 按岗位获取接收者
     */
    public ResultData<List<String>> getReceiverIdsByRole(String featureRoleCode) {
        Map<String, String> params = new HashMap<>();
        params.put("featureRoleCodes", featureRoleCode);
        return apiTemplate.getByAppModuleCode("sei-basic", "/featureRole/getUserIdsByFeatureRole", new ParameterizedTypeReference<ResultData<List<String>>>() {
        }, params);
    }
}
