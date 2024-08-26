package com.hhplus.concert.api.reservation.domain.entity;

import com.hhplus.concert.api.reservation.domain.type.ReservationEventStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "RESERVATION_CREATED_EVENT")
public class ReservationCreatedEvent {
    @Id
    @Column(name = "RESERVATION_CREATED_EVENT_ID")
    private String reservationCreatedEventId;
    @Column(name = "RESERVATION_ID")
    private Long reservationId;
    @Column(name = "RESERVATION_EVENT_STATUS")
    private ReservationEventStatus reservationEventStatus;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    public void updateStatus(ReservationEventStatus reservationEventStatus) {
        this.reservationEventStatus = reservationEventStatus;
    }
}
