package com.changhong.sei.notify.config;

import com.changhong.sei.notify.service.cust.BasicIntegration;
import com.changhong.sei.notify.service.cust.BasicIntegrationCustBase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实现功能：自定义业务逻辑扩展配置
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-25 06:37
 */
@Configuration
public class ServiceCustAutoConfig {
    /**
     * 公司业务逻辑扩展实现
     *
     * @return 扩展实现
     */
    @Bean
    @ConditionalOnMissingBean(BasicIntegration.class)
    public BasicIntegration corporationServiceCust() {
        return new BasicIntegrationCustBase();
    }
}
