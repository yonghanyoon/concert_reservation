package com.hhplus.concert.api.token.scheduler;

import com.hhplus.concert.api.token.application.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenScheduler {

    private final TokenService tokenService;

    @Scheduled(fixedDelay = 60000)
    public void processTokenExpired() {
        tokenService.tokenExpiredCheck();
    }

}
