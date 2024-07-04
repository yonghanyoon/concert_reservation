package com.hhplus.concert.domain;

import java.time.LocalDateTime;

public record Schedule(
    Long scheduleId,
    Long concertId,
    LocalDateTime scheduleDate
) {

}
