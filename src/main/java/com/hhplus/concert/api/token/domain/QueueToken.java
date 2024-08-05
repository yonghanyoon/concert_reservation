package com.hhplus.concert.api.token.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class QueueToken {
    private Long userId;
    private Long position;
    private String waitingTime;
}
