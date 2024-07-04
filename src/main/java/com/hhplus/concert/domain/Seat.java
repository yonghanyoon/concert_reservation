package com.hhplus.concert.domain;

public record Seat(
    Long seatId,
    Long seatNumber,
    Long scheduleId,
    Long price
) {

}
