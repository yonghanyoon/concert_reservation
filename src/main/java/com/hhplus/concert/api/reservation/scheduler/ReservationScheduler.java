package com.hhplus.concert.api.reservation.scheduler;

import com.hhplus.concert.api.reservation.application.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationScheduler {

    private final ReservationService reservationService;

    @Scheduled(fixedDelay = 60000)
    public void processReservationExpired() {
        reservationService.reservationExpiredCheck();
    }
}
