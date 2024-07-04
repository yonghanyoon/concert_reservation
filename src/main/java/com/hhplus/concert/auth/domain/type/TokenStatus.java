package com.hhplus.concert.auth.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenStatus {
    //reserved, available, expired
    RESERVED("1", "대기"),
    AVAILABLE("2", "입장"),
    EXPIRED("3", "만료");

    private String code;
    private String desc;
}
