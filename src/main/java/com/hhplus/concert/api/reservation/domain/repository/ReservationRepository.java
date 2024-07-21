package com.hhplus.concert.api.reservation.domain.repository;

import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import com.hhplus.concert.api.reservation.domain.type.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByReservationIdAndUserId(Long reservationId, Long userId);
    List<Reservation> findAllByReservationStatusAndReservationExpiryBefore(ReservationStatus reservationStatus, LocalDateTime reservationExpiry);
}
