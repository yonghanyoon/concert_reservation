package com.hhplus.concert.api.concert.domain.entity;

import com.hhplus.concert.api.concert.domain.type.SeatStatus;
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
}
