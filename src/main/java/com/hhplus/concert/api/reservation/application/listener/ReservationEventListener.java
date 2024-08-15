package com.hhplus.concert.api.reservation.application.listener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

import com.hhplus.concert.api.reservation.domain.event.ReservationEvent;
import com.hhplus.concert.api.reservation.infrastructure.kafka.KafkaReservationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ReservationEventListener {

    private final KafkaReservationProducer kafkaReservationProducer;

    @Async
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void sendReservationInfo(ReservationEvent event) {
        kafkaReservationProducer.send(new ReservationEvent(event.getMessageId(), event.getSeatIds()));
    }
}
