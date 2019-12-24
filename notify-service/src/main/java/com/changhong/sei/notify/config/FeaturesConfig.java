package com.changhong.sei.notify.config;

import org.apache.cxf.feature.Feature;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <strong>实现功能:</strong>
 * <p>CXF 功能配置</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2017-10-24 17:26
 */
@Configuration
public class FeaturesConfig {
    @Value("${cxf.path}")
    private String basePath;
    @Value("${notify.version}")
    private String version;

    @Bean("swagger2Feature")
    public Feature swagger2Feature() {
        Swagger2Feature result = new Swagger2Feature();
        result.setTitle("SEI Notify服务 API");
        result.setDescription("消息通知服务的API文档");
        result.setBasePath(this.basePath);
        result.setVersion(version);
        result.setSchemes(new String[] { "http", "https" });
        result.setPrettyPrint(true);
        return result;
    }
}
