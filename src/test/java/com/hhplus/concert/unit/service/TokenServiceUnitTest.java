package com.hhplus.concert.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.hhplus.concert.api.token.application.TokenService;
import com.hhplus.concert.api.token.domain.QueueToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TokenServiceUnitTest {

    private TokenService tokenService = Mockito.mock(TokenService.class);

    @DisplayName("대기열 토큰 발급 테스트")
    @Test
    public void saveTokenTest() {
        // given
        Long userId = 1L;
        QueueToken queueToken = QueueToken.builder()
            .userId(userId)
            .build();

        // when
        when(tokenService.createWaitingToken(queueToken)).thenReturn(queueToken);

        // then
        assertThat(tokenService.createWaitingToken(queueToken).getUserId()).isEqualTo(userId);
    }

    @DisplayName("대기열 토큰 조회 테스트")
    @Test
    public void getTokenTest() {
        // given
        Long userId = 1L;
        QueueToken queueToken = QueueToken.builder()
            .userId(userId)
            .position(1L)
            .waitingTime("대기 예상 시간: 09 시, 00 분, 00 초")
            .build();

        // when
        when(tokenService.getWaitingToken(userId)).thenReturn(queueToken);

        // then
        assertThat(tokenService.getWaitingToken(userId).getUserId()).isEqualTo(userId);
        assertThat(tokenService.getWaitingToken(userId).getPosition()).isEqualTo(1L);
        assertThat(tokenService.getWaitingToken(userId).getWaitingTime()).isEqualTo("대기 예상 시간: 09 시, 00 분, 00 초");
    }

}
