package com.hhplus.concert.api.reservation.domain.repository;

import com.hhplus.concert.api.reservation.domain.entity.PaymentHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    List<PaymentHistory> findAllByReservationId(Long reservationId);
}
