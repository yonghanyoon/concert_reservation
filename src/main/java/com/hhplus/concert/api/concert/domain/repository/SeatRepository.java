package com.hhplus.concert.api.concert.domain.repository;

import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScheduleIdAndSeatStatus(Long scheduleId, SeatStatus seatStatus);
    List<Seat> findAllBySeatIdInAndSeatStatus(List<Long> seatIds, SeatStatus status);
}
