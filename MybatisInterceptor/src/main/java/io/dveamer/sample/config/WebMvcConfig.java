package io.dveamer.sample.config;

import io.dveamer.sample.common.interceptor.http.AccountDiscoveryInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AccountDiscoveryInterceptor accountDiscoveryInterceptor;

    public WebMvcConfig(AccountDiscoveryInterceptor accountDiscoveryInterceptor) {
        this.accountDiscoveryInterceptor = accountDiscoveryInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accountDiscoveryInterceptor)
                .addPathPatterns("/**");
    }
}