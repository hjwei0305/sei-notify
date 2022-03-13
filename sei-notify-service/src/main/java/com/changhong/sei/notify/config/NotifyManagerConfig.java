package com.changhong.sei.notify.config;

import com.changhong.sei.notify.manager.email.EmailManager;
import com.changhong.sei.notify.manager.miniapp.MiniAppManager;
import com.changhong.sei.notify.manager.remind.RemindManager;
import com.changhong.sei.notify.manager.sms.SmsManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2022-03-11 14:22
 */
@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class NotifyManagerConfig {

    /**
     * @see com.changhong.sei.notify.dto.NotifyType#SEI_REMIND
     */
    @Bean("SEI_REMIND")
    public RemindManager remindManager() {
        return new RemindManager();
    }

    /**
     * @see com.changhong.sei.notify.dto.NotifyType#EMAIL
     */
    @Bean("EMAIL")
    public EmailManager emailManager() {
        return new EmailManager();
    }

    /**
     * @see com.changhong.sei.notify.dto.NotifyType#MiniApp
     */
    @Bean("MiniApp")
    public MiniAppManager miniAppManager() {
        return new MiniAppManager();
    }

    /**
     * @see com.changhong.sei.notify.dto.NotifyType#SMS
     */
    @Bean("SMS")
    public SmsManager smsManager(SmsProperties properties) {
        return new SmsManager(properties);
    }
}
