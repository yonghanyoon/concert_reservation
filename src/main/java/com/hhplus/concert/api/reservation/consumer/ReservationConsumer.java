package com.hhplus.concert.api.reservation.consumer;

import com.hhplus.concert.api.reservation.application.ReservationService;
import com.hhplus.concert.api.reservation.domain.event.ReservationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationConsumer {

    private final ReservationService reservationService;

    @KafkaListener(topics = "reservation-topic", groupId = "reservation-group")
    public void handleEvent(ReservationEvent event) {
        reservationService.reservationEventUpdate(event);
    }
}
