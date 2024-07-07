package com.hhplus.concert.api.reservation.domain;

import java.time.LocalDateTime;

public record Schedule(
    Long scheduleId,
    Long concertId,
    LocalDateTime scheduleDate
) {

}
