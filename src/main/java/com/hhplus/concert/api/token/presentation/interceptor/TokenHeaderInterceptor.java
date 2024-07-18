package com.hhplus.concert.api.token.presentation.interceptor;

import com.hhplus.concert.api.token.application.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class TokenHeaderInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = request.getHeader("uuid");

        if (uuid == null) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "UUID가 존재하지 않습니다.");
            return false;
        }
        tokenService.tokenStatusCheck(uuid);
        return true;
    }

}