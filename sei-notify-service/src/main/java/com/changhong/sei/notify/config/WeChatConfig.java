package com.changhong.sei.notify.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceOkHttpImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:微信配置
 * @date 2019/12/16 15:10
 */
@Configuration
@EnableConfigurationProperties(com.changhong.sei.notify.config.WxMpProperties.class)
@ConditionalOnProperty(prefix = "wx.mp", name = "enable", havingValue = "true")
public class WeChatConfig {

    @Autowired
    private WxMpProperties properties;

    /**
     * 注册微信服务
     *
     * @return WxMpService
     */
    @Bean
    public WxMpService wxMpService() {
        final WxMpProperties configs = this.properties;
        if (configs == null) {
            throw new RuntimeException("微信相关配置错误");
        }
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(configs.getAppId());
        configStorage.setSecret(configs.getSecret());
        configStorage.setToken(configs.getToken());
        configStorage.setAesKey(configs.getAesKey());
        WxMpService service = new WxMpServiceOkHttpImpl();
        service.setWxMpConfigStorage(configStorage);
        return service;
    }

}
