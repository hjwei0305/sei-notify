package com.changhong.sei.notify.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <strong>实现功能:</strong>
 * <p>数据实体映射配置</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 17:18
 */
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
