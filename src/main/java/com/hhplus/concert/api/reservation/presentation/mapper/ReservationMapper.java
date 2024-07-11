package com.hhplus.concert.api.reservation.presentation.mapper;

import com.hhplus.concert.api.reservation.domain.entity.PaymentHistory;
import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import com.hhplus.concert.api.reservation.domain.type.PaymentStatus;
import com.hhplus.concert.api.reservation.presentation.dto.request.PaymentReqDTO;
import com.hhplus.concert.api.reservation.presentation.dto.request.ReservationReqDTO;
import com.hhplus.concert.api.reservation.presentation.dto.response.PaymentResDTO;
import com.hhplus.concert.api.reservation.presentation.dto.response.ReservationResDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationMapper {

    public static Reservation toEntity(ReservationReqDTO dto) {
        if (dto == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setUserId(dto.getUserId());
        reservation.setConcertId(dto.getContentId());
        reservation.setScheduleId(dto.getScheduleId());
        List<ReservationSeat> reservationSeats = new ArrayList<>();
        for (Long seatId : dto.getSeatIdList()) {
            ReservationSeat reservationSeat = new ReservationSeat();
            reservationSeat.setSeatId(seatId);
            reservationSeat.setScheduleId(dto.getScheduleId());
            reservationSeat.setConcertId(dto.getContentId());
            reservationSeats.add(reservationSeat);
        }
        reservation.setReservationSeats(reservationSeats);
        return reservation;
    }

    public static ReservationResDTO toDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        ReservationResDTO dto = new ReservationResDTO();
        dto.setReservationId(reservation.getReservationId());
        dto.setConcertTitle(reservation.getConcertTitle());
        dto.setUserId(reservation.getUserId());
        dto.setSeatIdList(reservation.getReservationSeats().stream().map(i -> i.getSeatId()).collect(
            Collectors.toList()));
        dto.setTotalPrice(reservation.getTotalPrice());
        dto.setReservationExpiry(reservation.getReservationExpiry());
        return dto;
    }

    public static PaymentHistory toEntityFromPayment(PaymentReqDTO dto) {
        if (dto == null) {
            return null;
        }
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setReservationId(dto.getReservationId());
        paymentHistory.setUserId(dto.getUserId());
        paymentHistory.setAmount(dto.getAmount());
        paymentHistory.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentHistory.setPaymentTime(LocalDateTime.now());
        return paymentHistory;
    }

    public static PaymentResDTO toDtoFromPayment(PaymentHistory paymentHistory) {
        if (paymentHistory == null) {
            return null;
        }
        PaymentResDTO dto = new PaymentResDTO();
        dto.setPaymentId(paymentHistory.getPaymentId());
        dto.setPaymentTime(paymentHistory.getPaymentTime());
        return dto;
    }
}
