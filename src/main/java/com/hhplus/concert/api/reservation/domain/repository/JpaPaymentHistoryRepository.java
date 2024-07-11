package com.hhplus.concert.api.reservation.domain.repository;

import com.hhplus.concert.api.reservation.domain.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

}
