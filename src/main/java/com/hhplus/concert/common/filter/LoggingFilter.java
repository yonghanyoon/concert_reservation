package com.hhplus.concert.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // ContentCachingRequestWrapper와 ContentCachingResponseWrapper를 사용하여 요청과 응답을 래핑
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        // 체인을 통해 필터를 진행합니다.
        filterChain.doFilter(wrappedRequest, wrappedResponse);

        // 요청 로그 기록
        logRequest(wrappedRequest);

        // 응답 로그 기록
        logResponse(wrappedResponse);

        // 응답을 클라이언트로 보내기 위해서 내용을 복사합니다.
        wrappedResponse.copyBodyToResponse();
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String body = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
        log.info("Request URL: {}", request.getRequestURI());
        log.info("Request Method: {}", request.getMethod());
        log.info("Request Body: {}", body);
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        String body = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
        log.info("Response Status: {}", response.getStatus());
        log.info("Response Body: {}", body);
    }

}