package com.hhplus.concert.api.concert.infrastructure.repository;

import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScheduleIdAndSeatStatus(Long scheduleId, SeatStatus seatStatus);
}
