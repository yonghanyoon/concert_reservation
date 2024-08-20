package com.hhplus.concert.integration.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import com.hhplus.concert.api.reservation.application.ReservationService;
import com.hhplus.concert.api.reservation.domain.entity.ReservationCreatedEvent;
import com.hhplus.concert.api.reservation.domain.repository.ReservationCreatedEventRepository;
import com.hhplus.concert.api.reservation.domain.type.ReservationEventStatus;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback
public class SchedulerIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationCreatedEventRepository reservationCreatedEventRepository;
    private Long messageId;

    @BeforeEach
    public void setUp() {
        ReservationCreatedEvent event = new ReservationCreatedEvent(null,
                                                                    1L,
                                                                    ReservationEventStatus.INIT,
                                                                    LocalDateTime.now().minusMinutes(10));
        this.messageId = reservationCreatedEventRepository.save(event).getReservationCreatedEventId();
    }

    @DisplayName("outbox 스케줄러 테스트")
    @Test
    public void outbox_scheduler_test() {
        // given
        // when
        reservationService.processReservationEvent();
        // Then
        Optional<ReservationCreatedEvent> testEvent = reservationCreatedEventRepository.findById(messageId);
        assertThat(testEvent.get().getReservationEventStatus()).isEqualTo(ReservationEventStatus.FAILED);
    }
}
