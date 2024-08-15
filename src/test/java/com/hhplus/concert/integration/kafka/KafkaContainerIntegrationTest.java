package com.hhplus.concert.integration.kafka;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hhplus.concert.api.reservation.domain.event.ReservationEvent;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@DirtiesContext
@EnableKafka
public class KafkaContainerIntegrationTest {

    private static KafkaContainer kafkaContainer;
    @Autowired
    private KafkaTemplate<String, ReservationEvent> kafkaTemplate;

    private final CountDownLatch latch = new CountDownLatch(1);
    private ReservationEvent reservationEvent;

    @BeforeAll
    public static void setUp() {
        kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
        kafkaContainer.start();
        System.setProperty("spring.kafka.bootstrap-servers", kafkaContainer.getBootstrapServers());
    }

    @AfterAll
    public static void containerDown() {
        if (kafkaContainer != null) {
            kafkaContainer.stop();
        }
    }

    @KafkaListener(topics = "reservation-test-topic", groupId = "reservation-group")
    public void listen(ReservationEvent event) {
        this.reservationEvent = event;
        latch.countDown();
    }

    @DisplayName("Kafka 이벤트 메시지 발행 및 응답 테스트")
    @Test
    public void handle_event_test() throws InterruptedException {
        // given
        Long messageId = 1L;
        List<Long> seatIds = Arrays.asList(1L, 2L, 3L);
        ReservationEvent event = new ReservationEvent(messageId, seatIds);

        // when
        kafkaTemplate.send("reservation-test-topic", "reservation", event);

        // then
        boolean messageReceived = latch.await(10, TimeUnit.SECONDS);
        assertTrue(messageReceived);
        assertNotNull(reservationEvent);
        assertEquals(1L, reservationEvent.getMessageId());
        assertEquals(Arrays.asList(1L, 2L, 3L), reservationEvent.getSeatIds());
    }
}
