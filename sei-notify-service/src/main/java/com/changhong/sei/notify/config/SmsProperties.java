package com.changhong.sei.notify.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 手机短信服务配置
 */
@ConfigurationProperties(prefix = "sei.notify.sms")
public class SmsProperties {

    /**
     * 是否启用手机短信
     */
    private boolean enable;
    /**
     * 短信服务商
     */
    private String provider;

    /**
     * 服务地址
     */
    private String host;
    /**
     * 设置微信小程序的appid
     */
    private String appKey;

    /**
     * 设置微信小程序的Secret
     */
    private String secretKey;

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
