package com.hhplus.concert.config;

import com.hhplus.concert.api.token.interfaces.interceptor.TokenHeaderInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TokenHeaderInterceptor tokenHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenHeaderInterceptor)
            .addPathPatterns("/api/concerts/**")
            .addPathPatterns("/api/reservations/**");
    }
}
