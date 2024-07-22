package com.hhplus.concert.api.reservation.domain.repository;

import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat.ReservationSeatPk;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, ReservationSeatPk> {
    List<ReservationSeat> findAllBySeatIdInAndScheduleIdAndConcertId(List<Long> seatIds, Long scheduleId, Long concertId);

    void deleteAllBySeatIdIn(List<Long> seats);
}
