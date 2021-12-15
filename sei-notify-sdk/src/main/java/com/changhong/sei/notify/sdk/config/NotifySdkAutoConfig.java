package com.changhong.sei.notify.sdk.config;

import com.changhong.sei.notify.sdk.client.BasicClient;
import com.changhong.sei.notify.sdk.client.NotifyClient;
import com.changhong.sei.notify.sdk.manager.NotifyManager;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实现功能：开发工具包配置类
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-04-20 22:41
 */
@Configuration
@EnableFeignClients(basePackages = {"com.changhong.sei.notify.sdk.client"})
public class NotifySdkAutoConfig {

    @Bean
    public NotifyManager notifyManager(NotifyClient notifyClient, BasicClient basicClient) {
        return new NotifyManager(notifyClient, basicClient);
    }
}
