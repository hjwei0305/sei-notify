package com.changhong.sei.notify.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:微信小程序配置
 * @date 2019/12/16 15:10
 */
@Configuration
@EnableConfigurationProperties(WxMaProperties.class)
@ConditionalOnProperty(prefix = "wx.miniapp", name = "enable", havingValue = "true")
public class MiniProgramConfig {

    @Autowired
    private WxMaProperties properties;

    /**
     * 注册微信服务
     *
     * @return WxMaService
     */
    @Bean
    public WxMaService wxMaService() {
        final WxMaProperties configs = this.properties;
        if (configs == null) {
            throw new RuntimeException("微信小程序相关配置错误");
        }
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(configs.getAppid());
        config.setSecret(configs.getSecret());
        config.setToken(configs.getToken());
        config.setAesKey(configs.getAesKey());
        config.setMsgDataFormat(configs.getMsgDataFormat());

        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }

}
