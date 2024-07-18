package com.hhplus.concert.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // 요청 정보 로깅
        log.info("Request URI: {}, Method: {}, Content Type: {}, Remote Address: {}",
                    request.getRequestURI(),
                    request.getMethod(),
                    request.getContentType(),
                    request.getRemoteAddr());

        // 다음 필터로 체인 전달
        filterChain.doFilter(request, response);
    }
}
