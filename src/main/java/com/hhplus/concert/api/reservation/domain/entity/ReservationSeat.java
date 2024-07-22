package com.hhplus.concert.api.reservation.domain.entity;

import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import com.hhplus.concert.api.reservation.domain.type.ReservationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "RESERVATION_SEAT")
public class ReservationSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_SEAT_ID")
    private Long reservationSeatId;
    @Column(name = "SEAT_ID")
    private Long seatId;
    @Column(name = "SCHEDULE_ID")
    private Long scheduleId;
    @Column(name = "CONCERT_ID")
    private Long concertId;
    @Column(name = "RESERVATION_ID")
    private Long reservationId;
    @Column(name = "RESERVATION_STATUS")
    private ReservationStatus reservationStatus;


    public void updateReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

}
