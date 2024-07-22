package com.hhplus.concert.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.concert.api.reservation.application.ReservationService;
import com.hhplus.concert.api.reservation.domain.entity.PaymentHistory;
import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import com.hhplus.concert.api.reservation.domain.repository.ReservationSeatRepository;
import com.hhplus.concert.api.reservation.domain.type.ReservationStatus;
import com.hhplus.concert.common.exception.list.CustomBadRequestException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class ReservationServiceIntegrationTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationSeatRepository reservationSeatRepository;

    @DisplayName("결제 히스토리 실패 테스트")
    @Test
    void post_payment_fail_test() {
        // given
        String uuid = "5f093b13-2aec-4776-9ae8-a08b83c36495";
        PaymentHistory paymentHistory = PaymentHistory.builder()
            .reservationId(99L)
            .userId(18L)
            .amount(180000L)
            .build();
        // when
        CustomBadRequestException exception = assertThrows(CustomBadRequestException.class, () -> reservationService.postPayment(uuid, paymentHistory));
        // then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
        assertEquals("예약 정보가 없습니다.", exception.getMessage());
    }

    @DisplayName("좌석 예약 동시성 테스트")
    @Test
    public void concurrent_reservations_test() throws InterruptedException {
        // given
        Long scheduleId = 1L;
        Long concertId = 1L;
        List<ReservationSeat> reservationSeats = new ArrayList<>();
        reservationSeats.add(ReservationSeat.builder()
                                            .seatId(18L)
                                            .scheduleId(scheduleId)
                                            .concertId(concertId)
                                            .build());
        reservationSeats.add(ReservationSeat.builder()
                                            .seatId(19L)
                                            .scheduleId(scheduleId)
                                            .concertId(concertId)
                                            .build());
        reservationSeats.add(ReservationSeat.builder()
                                            .seatId(20L)
                                            .scheduleId(scheduleId)
                                            .concertId(concertId)
                                            .build());

        int numberOfExecute = 11;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(numberOfExecute);

        for (int i = 1; i <= numberOfExecute; i++) {
            Long id = Long.valueOf(i);
            executorService.execute(() -> {
                try {
                    reservationService.postReservationSeat(Reservation.builder()
                                                                      .userId(id)
                                                                      .concertId(concertId)
                                                                      .scheduleId(scheduleId)
                                                                      .reservationSeats(reservationSeats)
                                                                      .build());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        List<ReservationSeat> reservedSeats = reservationSeatRepository.findAllBySeatIdInAndScheduleIdAndConcertIdAndReservationStatus(
            reservationSeats.stream().map(ReservationSeat::getSeatId).collect(Collectors.toList()),
            scheduleId,
            concertId,
            ReservationStatus.STANDBY
        );

        assertEquals(reservedSeats.size(), reservationSeats.size());
    }
}
