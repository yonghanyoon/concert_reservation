package com.hhplus.concert.api.reservation.domain.type;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationEventStatus {
    INIT("1", "발송 대기"),
    PUBLISHED("2", "발송 성공"),
    FAILED("3", "발송 실패");

    private String code;
    private String desc;

    public static ReservationEventStatus of(String code) {
        return Arrays.stream(ReservationEventStatus.values())
                     .filter(reservationStatus -> code.equals(reservationStatus.getCode()))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("[ReservationEventStatus] 존재하지 않는 코드입니다. { " + code + " }"));
    }
}
