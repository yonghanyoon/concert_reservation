package com.hhplus.concert.api.reservation.domain.entity;

import com.hhplus.concert.api.reservation.domain.type.ReservationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
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
public class Reservation {
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
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESERVATION_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<ReservationSeat> reservationSeats;
    @Column(name = "CONCERT_TITLE")
    private String concertTitle;
    @Column(name = "TOTAL_PRICE")
    private Long totalPrice;
    @Column(name = "RESERVATION_STATUS")
    private ReservationStatus reservationStatus;
    @Column(name = "RESERVATION_EXPIRY")
    private LocalDateTime reservationExpiry;
}
