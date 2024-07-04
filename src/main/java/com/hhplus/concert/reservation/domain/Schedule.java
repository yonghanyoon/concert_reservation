package com.hhplus.concert.reservation.domain;

import java.time.LocalDateTime;

public record Schedule(
    Long scheduleId,
    Long concertId,
    LocalDateTime scheduleDate
) {

}
