package com.hhplus.concert.api.reservation.infrastructure.kafka;

import com.hhplus.concert.api.reservation.domain.event.ReservationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaReservationProducer {
    private final KafkaTemplate<String, ReservationEvent> kafkaTemplate;

    public void send(ReservationEvent event) {
        kafkaTemplate.send("reservation-topic", "reservation", event);
    }
}
