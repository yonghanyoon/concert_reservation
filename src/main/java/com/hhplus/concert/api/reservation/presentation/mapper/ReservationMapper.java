package com.hhplus.concert.api.reservation.presentation.mapper;

import com.hhplus.concert.api.reservation.domain.entity.PaymentHistory;
import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import com.hhplus.concert.api.reservation.presentation.dto.request.PaymentReqDTO;
import com.hhplus.concert.api.reservation.presentation.dto.request.ReservationReqDTO;
import com.hhplus.concert.api.reservation.presentation.dto.response.PaymentResDTO;
import com.hhplus.concert.api.reservation.presentation.dto.response.ReservationResDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationMapper {

    public static Reservation toEntity(ReservationReqDTO dto) {
        if (dto == null) {
            return null;
        }
        List<ReservationSeat> reservationSeats = new ArrayList<>();
        for (Long seatId : dto.getSeatIdList()) {
            ReservationSeat reservationSeat = new ReservationSeat().builder()
                .seatId(seatId)
                .scheduleId(dto.getScheduleId())
                .concertId(dto.getConcertId())
                .build();
            reservationSeats.add(reservationSeat);
        }
        Reservation reservation = new Reservation().builder()
            .userId(dto.getUserId())
            .concertId(dto.getConcertId())
            .scheduleId(dto.getScheduleId())
            .reservationSeats(reservationSeats)
            .build();
        return reservation;
    }

    public static ReservationResDTO toDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        ReservationResDTO dto = new ReservationResDTO().builder()
            .reservationId(reservation.getReservationId())
            .concertTitle(reservation.getConcertTitle())
            .userId(reservation.getUserId())
            .seatIdList(reservation.getReservationSeats().stream().map(i -> i.getSeatId()).collect(
                Collectors.toList()))
            .totalPrice(reservation.getTotalPrice())
            .reservationExpiry(reservation.getReservationExpiry())
            .build();
        return dto;
    }

    public static PaymentHistory toEntityFromPayment(PaymentReqDTO dto) {
        if (dto == null) {
            return null;
        }
        PaymentHistory paymentHistory = new PaymentHistory().builder()
            .reservationId(dto.getReservationId())
            .userId(dto.getUserId())
            .amount(dto.getAmount())
            .build();
        return paymentHistory;
    }

    public static PaymentResDTO toDtoFromPayment(PaymentHistory paymentHistory) {
        if (paymentHistory == null) {
            return null;
        }
        PaymentResDTO dto = new PaymentResDTO().builder()
            .paymentId(paymentHistory.getPaymentId())
            .paymentTime(paymentHistory.getPaymentTime())
            .build();
        return dto;
    }
}
