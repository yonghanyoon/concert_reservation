package com.hhplus.concert.api.concert.domain.type;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatStatus {
    AVAILABLE("1", "예약 가능"),
    IMPOSSIBLE("2", "예약 불가");

    private String code;
    private String desc;

    public static SeatStatus of(String code) {
        return Arrays.stream(SeatStatus.values())
                     .filter(seatStatus -> code.equals(seatStatus.getCode()))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("[SeatStatus] 존재하지 않는 코드입니다. { " + code + " }"));
    }
}
