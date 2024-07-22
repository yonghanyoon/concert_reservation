package com.hhplus.concert.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.hhplus.concert.api.reservation.application.ReservationService;
import com.hhplus.concert.api.reservation.domain.entity.PaymentHistory;
import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import com.hhplus.concert.api.reservation.domain.type.PaymentStatus;
import com.hhplus.concert.api.reservation.domain.type.ReservationStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ReservationServiceUnitTest {

    private ReservationService reservationService = Mockito.mock(ReservationService.class);

    @DisplayName("좌석 예약 테스트")
    @Test
    public void postReservationSeatTest() {
        // given
        List<ReservationSeat> reservationSeats = new ArrayList<>();
        ReservationSeat reservationSeat = new ReservationSeat(1L, 1L, 1L, 1L, 1L, null);
        reservationSeats.add(reservationSeat);
        Reservation reservation = Reservation.builder()
            .reservationId(1L)
            .userId(1L)
            .concertTitle("발라드 황제 이석범 콘서트")
            .reservationStatus(ReservationStatus.SUCCESS)
            .reservationExpiry(LocalDateTime.of(2024, 6, 28, 12, 0))
            .totalPrice(180000L)
            .reservationSeats(reservationSeats)
            .build();

        // when
        when(reservationService.postReservationSeat(reservation)).thenReturn(reservation);

        // then
        assertThat(reservationService.postReservationSeat(reservation).getReservationId()).isEqualTo(1L);
        assertThat(reservationService.postReservationSeat(reservation).getConcertTitle()).isEqualTo("발라드 황제 이석범 콘서트");
        assertThat(reservationService.postReservationSeat(reservation).getUserId()).isEqualTo(1L);
        assertThat(reservationService.postReservationSeat(reservation).getReservationSeats().get(0).getSeatId()).isEqualTo(1L);
        assertThat(reservationService.postReservationSeat(reservation).getTotalPrice()).isEqualTo(180000L);
        assertThat(reservationService.postReservationSeat(reservation).getReservationExpiry()).isEqualTo("2024-06-28T12:00:00");
    }

    @DisplayName("결제 히스토리 테스트")
    @Test
    public void postPaymentTest() {
        // given
        String uuid = "011b60f5-dfd9-4975-9a23-1ef9953c0c22";
        PaymentHistory paymentHistory = new PaymentHistory(1L, 1L, 180000L, 1L, PaymentStatus.SUCCESS, LocalDateTime.of(2024, 6, 28, 12, 0));

        // when
        when(reservationService.postPayment(uuid, paymentHistory)).thenReturn(paymentHistory);

        // then
        assertThat(reservationService.postPayment(uuid, paymentHistory).getPaymentId()).isEqualTo(1L);
        assertThat(reservationService.postPayment(uuid, paymentHistory).getPaymentTime()).isEqualTo("2024-06-28T12:00:00");
    }
}
