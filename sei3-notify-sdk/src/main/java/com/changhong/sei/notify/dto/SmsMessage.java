package com.changhong.sei.notify.dto;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：平台发送的消息通知
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017-06-15 15:18      王锦光(wangj)                新建
 * <p/>
 * *************************************************************************************************
 */
public class SmsMessage implements Serializable {
    private static final long serialVersionUID = 7423461347998415587L;
    /**
     * 内容
     */
    private String content;
    /**
     * 电话清单
     */
    private List<String> phoneNums;
    /**
     * 内容模板代码
     */
    private String contentTemplateCode;
    /**
     * 内容模板参数
     */
    private Map<String, Object> contentTemplateParams;

    public SmsMessage() {
    }

    public SmsMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPhoneNums() {
        return phoneNums;
    }

    public void setPhoneNums(List<String> phoneNums) {
        this.phoneNums = phoneNums;
    }

    public String getContentTemplateCode() {
        return contentTemplateCode;
    }

    public void setContentTemplateCode(String contentTemplateCode) {
        this.contentTemplateCode = contentTemplateCode;
    }

    public Map<String, Object> getContentTemplateParams() {
        return contentTemplateParams;
    }

    public void setContentTemplateParams(Map<String, Object> contentTemplateParams) {
        this.contentTemplateParams = contentTemplateParams;
    }

    public List<String> addPhoneNum(String phoneNum) {
        if (Objects.isNull(phoneNums)) {
            phoneNums = new ArrayList<>();
        }
        if (StringUtils.isNotBlank(phoneNum)) {
            phoneNums.add(phoneNum);
        }
        return phoneNums;
    }

    public List<String> addPhoneNums(Collection<String> nums) {
        if (Objects.isNull(phoneNums)) {
            phoneNums = new ArrayList<>();
        }
        if (CollectionUtils.isNotEmpty(nums)) {
            phoneNums.addAll(nums);
        }
        return phoneNums;
    }
}
