package com.hhplus.concert.api.reservation.domain.repository;

import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import com.hhplus.concert.api.reservation.domain.type.ReservationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {
    List<ReservationSeat> findAllBySeatIdInAndScheduleIdAndConcertIdAndReservationStatus(List<Long> seatIds, Long scheduleId, Long concertId, ReservationStatus reservationStatus);
}
