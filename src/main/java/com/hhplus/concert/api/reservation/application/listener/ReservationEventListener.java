package com.hhplus.concert.api.reservation.application.listener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;
import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

import com.hhplus.concert.api.reservation.domain.entity.ReservationCreatedEvent;
import com.hhplus.concert.api.reservation.domain.event.ReservationEvent;
import com.hhplus.concert.api.reservation.domain.repository.ReservationCreatedEventRepository;
import com.hhplus.concert.api.reservation.domain.type.ReservationEventStatus;
import com.hhplus.concert.api.reservation.infrastructure.kafka.KafkaReservationProducer;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ReservationEventListener {

    private final KafkaReservationProducer kafkaReservationProducer;
    private final ReservationCreatedEventRepository reservationCreatedEventRepository;

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void createdReservationOutbox(ReservationEvent event) {
        reservationCreatedEventRepository.save(new ReservationCreatedEvent(event.getUuid(), event.getReservation().getReservationId(), ReservationEventStatus.INIT, LocalDateTime.now())).getReservationCreatedEventId();
    }

    @Async
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void sendReservationInfo(ReservationEvent event) {
        kafkaReservationProducer.send(new ReservationEvent(event.getUuid(), event.getReservation()));
    }
}
