package com.hhplus.concert.api.reservation.domain.repository;

import com.hhplus.concert.api.reservation.domain.entity.ReservationCreatedEvent;
import com.hhplus.concert.api.reservation.domain.type.ReservationEventStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationCreatedEventRepository extends JpaRepository<ReservationCreatedEvent, Long> {
    List<ReservationCreatedEvent> findAllByReservationEventStatus(ReservationEventStatus reservationEventStatus);
}