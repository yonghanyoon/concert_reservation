package com.hhplus.concert.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.hhplus.concert.api.token.application.TokenService;
import com.hhplus.concert.api.token.domain.entity.QueueToken;
import com.hhplus.concert.api.token.domain.type.TokenStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TokenServiceTest {

    private TokenService tokenService = Mockito.mock(TokenService.class);

    @DisplayName("대기열 토큰 발급 테스트")
    @Test
    public void saveTokenTest() {
        // given
        QueueToken queueToken = new QueueToken();
        queueToken.setTokenId(1L);
        queueToken.setUuid("011b60f5-dfd9-4975-9a23-1ef9953c0c22");
        queueToken.setTokenStatus(TokenStatus.RESERVED);
        queueToken.setCreateDt(LocalDateTime.now());

        // when
        when(tokenService.saveToken(queueToken)).thenReturn(queueToken);

        // then
        assertThat(tokenService.saveToken(queueToken).getUuid()).isEqualTo("011b60f5-dfd9-4975-9a23-1ef9953c0c22");
    }

    @DisplayName("대기열 토큰 조회 테스트")
    @Test
    public void getTokenTest() {
        // given
        String uuid = "011b60f5-dfd9-4975-9a23-1ef9953c0c22";
        QueueToken queueToken = new QueueToken();
        queueToken.setPosition(1L);
        queueToken.setTokenStatus(TokenStatus.RESERVED);
        queueToken.setExpirationTime(LocalDateTime.of(2024, 6, 28, 12, 0));

        // when
        when(tokenService.getToken(uuid)).thenReturn(queueToken);

        // then
        assertThat(tokenService.getToken(uuid).getPosition()).isEqualTo(1L);
        assertThat(tokenService.getToken(uuid).getTokenStatus()).isEqualTo(TokenStatus.RESERVED);
        assertThat(tokenService.getToken(uuid).getExpirationTime()).isEqualTo("2024-06-28T12:00:00");
    }

}
