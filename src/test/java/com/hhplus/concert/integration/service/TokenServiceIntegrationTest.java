//package com.hhplus.concert.integration.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import com.hhplus.concert.api.token.application.TokenService;
//import com.hhplus.concert.api.token.domain.entity.QueueToken;
//import com.hhplus.concert.api.token.domain.type.TokenStatus;
//import com.hhplus.concert.common.exception.list.CustomBadRequestException;
//import com.hhplus.concert.common.exception.list.CustomForbiddenException;
//import com.hhplus.concert.common.exception.list.CustomNotFoundException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//
//@SpringBootTest
//public class TokenServiceIntegrationTest {
//
//    @Autowired
//    private TokenService tokenService;
//
//    @DisplayName("인터셉터 토큰 검증 존재하지 않는 UUID 실패 테스트")
//    @Test
//    void interceptor_token_validate_not_found_uuid_fail_test() {
//        // given
//        String uuid = "not_uuid";
//        // when
//        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> tokenService.tokenStatusCheck(uuid));
//        // then
//        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
//        assertEquals("해당 UUID는 존재하지 않습니다.", exception.getMessage());
//    }
//
//    @DisplayName("인터셉터 토큰 검증 권한 실패 테스트")
//    @Test
//    void interceptor_token_validate_forbidden_fail_test() {
//        // given
//        String uuid = "c7fad26e-08f6-4ba3-bc67-6b7f7807cab1";
//        // when
//        CustomForbiddenException exception = assertThrows(CustomForbiddenException.class, () -> tokenService.tokenStatusCheck(uuid));
//        // then
//        assertEquals(HttpStatus.FORBIDDEN, exception.getErrorCode());
//        assertEquals("접근 권한 없음", exception.getMessage());
//    }
//
//    @DisplayName("토큰 발급 테스트")
//    @Test
//    void save_token_test() {
//        // given
//        QueueToken queueToken = QueueToken.builder().userId(1L).build();
//        // when
//        String uuid = tokenService.saveToken(queueToken).getUuid();
//        // then
//        assertEquals(queueToken.getUuid(), uuid);
//    }
//
//    @DisplayName("이미 토큰이 발급된 실패 테스트")
//    @Test
//    void save_token_fail_test() {
//        // given
//        QueueToken queueToken = QueueToken.builder().userId(1L).build();
//        // when
//        CustomBadRequestException exception = assertThrows(CustomBadRequestException.class, () -> tokenService.saveToken(queueToken));
//        // then
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
//        assertEquals("이미 토큰이 발급된 사용자", exception.getMessage());
//    }
//
//    @DisplayName("존재하지 않는 UUID 토큰 조회 실패 테스트")
//    @Test
//    void get_token_fail_test() {
//        // given
//        String uuid = "not_uuid";
//        // when
//        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> tokenService.getToken(uuid));
//        // then
//        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
//        assertEquals("해당 UUID는 존재하지 않습니다.", exception.getMessage());
//    }
//
//    @DisplayName("토큰 조회 테스트")
//    @Test
//    void get_token_test() {
//        // given
//        String uuid = "5f093b13-2aec-4776-9ae8-a08b83c36495";
//        // when
//        QueueToken queueToken = tokenService.getToken(uuid);
//        // then
//        assertEquals(queueToken.getTokenStatus(), TokenStatus.AVAILABLE);
//    }
//}
