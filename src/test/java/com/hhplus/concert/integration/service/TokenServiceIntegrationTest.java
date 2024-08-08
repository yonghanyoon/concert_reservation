package com.hhplus.concert.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.concert.api.token.application.TokenService;
import com.hhplus.concert.api.token.domain.QueueToken;
import com.hhplus.concert.common.exception.list.CustomBadRequestException;
import com.hhplus.concert.common.exception.list.CustomForbiddenException;
import com.hhplus.concert.common.exception.list.CustomNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class TokenServiceIntegrationTest {

    @Autowired
    private TokenService tokenService;

    @DisplayName("인터셉터 토큰 검증 존재하지 않는 userId 실패 테스트")
    @Test
    void interceptor_token_validate_not_found_uuid_fail_test() {
        // given
        // when
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> tokenService.tokenActiveCheck(null));
        // then
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
        assertEquals("userId가 존재하지 않습니다.", exception.getMessage());
    }

    @DisplayName("인터셉터 토큰 검증 권한 실패 테스트")
    @Test
    void interceptor_token_validate_forbidden_fail_test() {
        // given
        Long userId = 812412555121L;
        // when
        CustomForbiddenException exception = assertThrows(CustomForbiddenException.class, () -> tokenService.tokenActiveCheck(userId));
        // then
        assertEquals(HttpStatus.FORBIDDEN, exception.getErrorCode());
        assertEquals("접근 권한 없음", exception.getMessage());
    }

    @DisplayName("토큰 발급 테스트")
    @Test
    void save_token_test() {
        // given
        QueueToken queueToken = QueueToken.builder().userId(1L).build();
        // when
        Long userId = tokenService.createWaitingToken(queueToken).getUserId();
        // then
        assertEquals(queueToken.getUserId(), userId);
    }

    @DisplayName("이미 토큰이 발급된 실패 테스트")
    @Test
    void save_token_fail_test() {
        // given
        QueueToken queueToken = QueueToken.builder().userId(1L).build();
        // when
        CustomBadRequestException exception = assertThrows(CustomBadRequestException.class, () -> tokenService.createWaitingToken(queueToken));
        // then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
        assertEquals("이미 토큰이 발급된 사용자", exception.getMessage());
    }

    @DisplayName("존재하지 않는 userId 토큰 조회 실패 테스트")
    @Test
    void get_token_fail_test() {
        // given
        Long userId = 136516616L;
        // when
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> tokenService.getWaitingToken(userId));
        // then
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
        assertEquals("존재하지 않는 토큰", exception.getMessage());
    }

    @DisplayName("토큰 조회 테스트")
    @Test
    void get_token_test() {
        // given
        Long userId = 1L;
        // when
        QueueToken queueToken = tokenService.getWaitingToken(userId);
        // then
        assertEquals(queueToken.getUserId(), userId);
    }
}
