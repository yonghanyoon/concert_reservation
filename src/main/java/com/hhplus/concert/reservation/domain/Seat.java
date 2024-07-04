package com.hhplus.concert.reservation.domain;

public record Seat(
    Long seatId,
    Long seatNumber,
    Long scheduleId,
    Long price
) {

}
