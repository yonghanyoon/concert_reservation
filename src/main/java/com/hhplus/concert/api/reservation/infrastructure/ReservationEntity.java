package com.hhplus.concert.api.reservation.infrastructure;

import com.hhplus.concert.api.reservation.infrastructure.type.ReservationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "RESERVATION")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Long reservationId;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "CONCERT_ID")
    private Long concertId;
    @Column(name = "SCHEDULE_ID")
    private Long scheduleId;
    @Column(name = "SEAT_ID")
    private Long seatId;
    @Column(name = "CONCERT_TITLE")
    private String concertTitle;
    @Column(name = "TOTAL_PRICE")
    private Long totalPrice;
    @Column(name = "RESERVATION_STATUS")
    private ReservationStatus reservationStatus;
}
