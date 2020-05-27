package com.changhong.sei.notify.sdk.config;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.notify.sdk.manager.NotifyManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实现功能：开发工具包配置类
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-04-20 22:41
 */
@Configuration
public class NotifySdkAutoConfig {

    @Bean
    public NotifyManager notifyManager(ApiTemplate apiTemplate) {
        return new NotifyManager(apiTemplate);
    }
}
