package com.hhplus.concert.integration.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.hhplus.concert.api.reservation.domain.event.ReservationEvent;
import com.hhplus.concert.api.reservation.infrastructure.DataPlatformMockApiClient;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
public class DataPlatformConsumerIntegrationTest {

    @Autowired
    private KafkaTemplate<String, ReservationEvent> kafkaTemplate;

    @Autowired
    private DataPlatformMockApiClient dataPlatformMockApiClient;

    @DisplayName("kafka 이벤트 메시지 발행 및 응답 테스트")
    @Test
    public void handle_event_test() throws InterruptedException {
        // given
        Long messageId = 1L;
        List<Long> seatIds = Arrays.asList(1L, 2L, 3L);
        ReservationEvent event = new ReservationEvent(messageId, seatIds);

        // when
        kafkaTemplate.send("reservation-topic", "reservation", event);

        // then
        Thread.sleep(2000);
        assertEquals(dataPlatformMockApiClient.sendData(seatIds), true);
    }
}
