package com.hhplus.concert.api.reservation.domain;

public record Seat(
    Long seatId,
    Long seatNumber,
    Long scheduleId,
    Long price
) {

}
