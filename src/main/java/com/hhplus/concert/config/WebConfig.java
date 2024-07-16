package com.hhplus.concert.config;

import com.hhplus.concert.api.token.interfaces.interceptor.TokenHeaderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenHeaderInterceptor tokenHeaderInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenHeaderInterceptor)
            .addPathPatterns("/api/concerts/**")
            .addPathPatterns("/api/reservations/**");
    }
}
