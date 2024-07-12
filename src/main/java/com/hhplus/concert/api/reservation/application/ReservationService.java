package com.hhplus.concert.api.reservation.application;

import com.hhplus.concert.api.auth.application.AuthService;
import com.hhplus.concert.api.concert.application.ConcertService;
import com.hhplus.concert.api.reservation.domain.entity.PaymentHistory;
import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import com.hhplus.concert.api.reservation.domain.repository.JpaPaymentHistoryRepository;
import com.hhplus.concert.api.reservation.domain.repository.JpaReservationRepository;
import com.hhplus.concert.api.reservation.domain.repository.JpaReservationSeatRepository;
import com.hhplus.concert.api.reservation.domain.type.PaymentStatus;
import com.hhplus.concert.api.reservation.domain.type.ReservationStatus;
import com.hhplus.concert.exception.list.CustomBadRequestException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final JpaReservationRepository jpaReservationRepository;
    private final JpaReservationSeatRepository jpaReservationSeatRepository;
    private final JpaPaymentHistoryRepository jpaPaymentHistoryRepository;
    private final AuthService authService;
    private final ConcertService concertService;

    @Transactional
    public Reservation postReservationSeat(String uuid, Reservation reservation) {
        authService.tokenStatusCheck(uuid);
        reservation.setConcertTitle(concertService.getConcertTitle(reservation.getConcertId()));
        reservation.setReservationStatus(ReservationStatus.SUCCESS);
        reservation.setReservationExpiry(LocalDateTime.now().plusMinutes(10));
        List<Long> seatIds = reservation.getReservationSeats().stream().map(i -> i.getSeatId()).collect(
            Collectors.toList());
        reservation.setTotalPrice(concertService.getSeatTotalPrice(seatIds));
        jpaReservationRepository.save(reservation);
        List<ReservationSeat> reservationSeats = jpaReservationSeatRepository.findAllBySeatIdInAndScheduleIdAndConcertId(seatIds,
                                                                                                      reservation.getScheduleId(),
                                                                                                      reservation.getConcertId());
        if (reservationSeats.size() != 0) {
            throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "이미 예약된 좌석입니다.");
        }
        for (ReservationSeat item : reservation.getReservationSeats()) {
            ReservationSeat seat = new ReservationSeat();
            seat.setSeatId(item.getSeatId());
            seat.setScheduleId(item.getScheduleId());
            seat.setConcertId(item.getConcertId());
            seat.setReservation(new Reservation().builder()
                                    .reservationId(reservation.getReservationId())
                                    .build());
            reservationSeats.add(seat);
        }
        jpaReservationSeatRepository.saveAll(reservationSeats);

        concertService.seatStatusImpossible(seatIds);
        return reservation;
    }

    @Transactional
    public PaymentHistory postPayment(String uuid, PaymentHistory paymentHistory) {
        authService.tokenStatusCheck(uuid);
        jpaReservationRepository.findByReservationIdAndUserId(
            paymentHistory.getReservationId(), paymentHistory.getUserId()).orElseThrow(() -> {
            throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "예약 정보가 없습니다.");
        });

        paymentHistory.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentHistory.setPaymentTime(LocalDateTime.now());
        jpaPaymentHistoryRepository.save(paymentHistory);
        authService.tokenExpired(uuid);

        return paymentHistory;
    }
}
