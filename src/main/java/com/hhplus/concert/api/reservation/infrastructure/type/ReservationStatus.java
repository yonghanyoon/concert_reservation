package com.hhplus.concert.api.reservation.infrastructure.type;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    CANCEL("1", "예약 취소"),
    SUCCESS("2", "예약 성공");

    private String code;
    private String desc;

    public static ReservationStatus of(String code) {
        return Arrays.stream(ReservationStatus.values())
                     .filter(reservationStatus -> code.equals(reservationStatus.getCode()))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("[ReservationStatus] 존재하지 않는 코드입니다. { " + code + " }"));
    }
}
