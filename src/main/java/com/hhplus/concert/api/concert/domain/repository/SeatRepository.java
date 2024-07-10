package com.hhplus.concert.api.concert.domain.repository;

import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import java.util.List;

public interface SeatRepository {
    List<Seat> findByScheduleIdAndSeatStatus(Long scheduleId, SeatStatus seatStatus);
}
