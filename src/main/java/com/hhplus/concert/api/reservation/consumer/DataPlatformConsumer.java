package com.hhplus.concert.api.reservation.consumer;

import com.hhplus.concert.api.reservation.domain.event.ReservationEvent;
import com.hhplus.concert.api.reservation.infrastructure.DataPlatformMockApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataPlatformConsumer {

    private final DataPlatformMockApiClient dataPlatformMockApiClient;

    @KafkaListener(topics = "reservation-topic", groupId = "reservation-group1")
    public void handleEvent(ReservationEvent event, Acknowledgment acknowledgment) {
        dataPlatformMockApiClient.sendData(event.getSeatIds());
        acknowledgment.acknowledge();
    }
}
