package com.hhplus.concert.api.concert.domain.entity;

import com.hhplus.concert.api.concert.domain.type.SeatStatus;
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
@Table(name = "SEAT")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEAT_ID")
    private Long seatId;
    @Column(name = "SEAT_STATUS")
    private SeatStatus seatStatus;
    @Column(name = "SEAT_NUMBER")
    private Long seatNumber;
    @Column(name = "SCHEDULE_ID")
    private Long scheduleId;
    @Column(name = "PRICE")
    private Long price;
    @Column(name = "USER_ID")
    private Long userId;

    public void updateSeatStatus(SeatStatus seatStatus, Long userId) {
        this.seatStatus = seatStatus;
        this.userId = userId;
    }
}
