package com.hhplus.concert.integration.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.hhplus.concert.api.reservation.domain.event.ReservationEvent;
import com.hhplus.concert.api.reservation.infrastructure.DataPlatformMockApiClient;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "reservation-topic" }, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class EmbeddedKafkaIntegrationTest {

    @Autowired
    private KafkaTemplate<String, ReservationEvent> kafkaTemplate;
    @Autowired
    private DataPlatformMockApiClient dataPlatformMockApiClient;
    private ReservationEvent reservationEvent;

    @KafkaListener(topics = "reservation-test-topic", groupId = "test-group")
    public void listen(ReservationEvent event) {
        this.reservationEvent = event;
    }

    @DisplayName("kafka 이벤트 메시지 발행 및 응답 테스트")
    @Test
    public void handle_event_test() throws InterruptedException {
        // given
        String uuid = "cea83dbd-3549-467f-852c-86f9c5089cf0";
        List<Long> seatIds = Arrays.asList(1L, 2L, 3L);
        ReservationEvent event = new ReservationEvent(uuid, seatIds);

        // when
        kafkaTemplate.send("reservation-test-topic", "reservation", event);

        // then
        Thread.sleep(2000);
        assertEquals(dataPlatformMockApiClient.sendData(seatIds), true);
        assertNotNull(reservationEvent);
        assertEquals(1L, reservationEvent.getUuid());
        assertEquals(Arrays.asList(1L, 2L, 3L), reservationEvent.getSeatIds());
    }
}
