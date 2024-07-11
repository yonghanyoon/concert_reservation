package com.hhplus.concert.api.auth.domain.type;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(name = "TokenStatus", description = "토큰 상태", enumAsRef = true)
@Getter
@AllArgsConstructor
public enum TokenStatus {
    RESERVED("1", "대기"),
    AVAILABLE("2", "입장"),
    EXPIRED("3", "만료");

    private String code;
    private String desc;

    public static TokenStatus of(String code) {
        return Arrays.stream(TokenStatus.values())
                     .filter(tokenStatus -> code.equals(tokenStatus.getCode()))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("[TokenStatus] 존재하지 않는 코드입니다. { " + code + " }"));
    }
}
