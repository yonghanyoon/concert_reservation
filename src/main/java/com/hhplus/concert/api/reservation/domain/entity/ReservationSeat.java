package com.hhplus.concert.api.reservation.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "RESERVATION_SEAT")
@IdClass(ReservationSeat.ReservationSeatPk.class)
public class ReservationSeat {
    @Id
    @Column(name = "SEAT_ID")
    private Long seatId;
    @Id
    @Column(name = "SCHEDULE_ID")
    private Long scheduleId;
    @Id
    @Column(name = "CONCERT_ID")
    private Long concertId;
    @Column(name = "RESERVATION_ID")
    private Long reservationId;


    @EqualsAndHashCode
    @NoArgsConstructor
    public static class ReservationSeatPk implements Serializable {
        private Long seatId;
        private Long scheduleId;
        private Long concertId;

        @Builder
        public ReservationSeatPk(Long seatId, Long scheduleId, Long concertId) {
            this.seatId = seatId;
            this.scheduleId = scheduleId;
            this.concertId = concertId;
        }
    }

}
