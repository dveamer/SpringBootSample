package io.dveamer.sample.config;

import io.dveamer.sample.common.interceptor.mybatis.RequiredParamInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MybatisConfig {

    @Bean
    RequiredParamInterceptor requiredParamInterceptor() {
        return new RequiredParamInterceptor();
    }
}
