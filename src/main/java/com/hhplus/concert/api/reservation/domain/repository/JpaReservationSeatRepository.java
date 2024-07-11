package com.hhplus.concert.api.reservation.domain.repository;

import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {
    List<ReservationSeat> findAllBySeatIdInAndScheduleIdAndConcertId(List<Long> seatIds, Long scheduleId, Long concertId);
}
