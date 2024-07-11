package com.hhplus.concert.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.hhplus.concert.api.reservation.application.ReservationService;
import com.hhplus.concert.api.reservation.domain.entity.PaymentHistory;
import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import com.hhplus.concert.api.reservation.domain.type.ReservationStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ReservationServiceTest {

    private ReservationService reservationService = Mockito.mock(ReservationService.class);

    @DisplayName("좌석 예약 테스트")
    @Test
    public void postReservationSeatTest() {
        // given
        String uuid = "011b60f5-dfd9-4975-9a23-1ef9953c0c22";
        Reservation reservation = new Reservation();
        reservation.setReservationId(1L);
        reservation.setUserId(1L);
        reservation.setConcertTitle("발라드 황제 이석범 콘서트");
        reservation.setReservationStatus(ReservationStatus.SUCCESS);
        reservation.setReservationExpiry(LocalDateTime.of(2024, 6, 28, 12, 0));
        reservation.setTotalPrice(180000L);
        List<ReservationSeat> reservationSeats = new ArrayList<>();
        ReservationSeat reservationSeat = new ReservationSeat();
        reservationSeat.setSeatId(1L);
        reservationSeat.setConcertId(1L);
        reservationSeat.setScheduleId(1L);
        reservationSeat.setReservation(new Reservation().builder().reservationId(1L).build());
        reservationSeats.add(reservationSeat);
        reservation.setReservationSeats(reservationSeats);

        // when
        when(reservationService.postReservationSeat(uuid, reservation)).thenReturn(reservation);

        // then
        assertThat(reservationService.postReservationSeat(uuid, reservation).getReservationId()).isEqualTo(1L);
        assertThat(reservationService.postReservationSeat(uuid, reservation).getConcertTitle()).isEqualTo("발라드 황제 이석범 콘서트");
        assertThat(reservationService.postReservationSeat(uuid, reservation).getUserId()).isEqualTo(1L);
        assertThat(reservationService.postReservationSeat(uuid, reservation).getReservationSeats().get(0).getSeatId()).isEqualTo(1L);
        assertThat(reservationService.postReservationSeat(uuid, reservation).getTotalPrice()).isEqualTo(180000L);
        assertThat(reservationService.postReservationSeat(uuid, reservation).getReservationExpiry()).isEqualTo("2024-06-28T12:00:00");
    }

    @DisplayName("결제 히스토리 테스트")
    @Test
    public void postPaymentTest() {
        // given
        String uuid = "011b60f5-dfd9-4975-9a23-1ef9953c0c22";
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setUserId(1L);
        paymentHistory.setReservationId(1L);
        paymentHistory.setAmount(180000L);
        paymentHistory.setPaymentId(1L);
        paymentHistory.setPaymentTime(LocalDateTime.of(2024, 6, 28, 12, 0));

        // when
        when(reservationService.postPayment(uuid, paymentHistory)).thenReturn(paymentHistory);

        // then
        assertThat(reservationService.postPayment(uuid, paymentHistory).getPaymentId()).isEqualTo(1L);
        assertThat(reservationService.postPayment(uuid, paymentHistory).getPaymentTime()).isEqualTo("2024-06-28T12:00:00");
    }
}
