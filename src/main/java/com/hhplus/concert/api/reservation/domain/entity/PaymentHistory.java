package com.hhplus.concert.api.reservation.domain.entity;

import com.hhplus.concert.api.reservation.domain.type.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "PAYMENT_HISTORY")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private Long paymentId;
    @Column(name = "RESERVATION_ID")
    private Long reservationId;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "AMOUNT")
    private Long amount;
    @Column(name = "PAYMENT_STATUS")
    private PaymentStatus paymentStatus;
    @Column(name = "PYAMENT_TIME")
    private LocalDateTime paymentTime;
}