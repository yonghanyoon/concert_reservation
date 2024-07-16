package com.hhplus.concert.api.reservation.application;

import com.hhplus.concert.api.balance.application.BalanceService;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import com.hhplus.concert.api.token.application.TokenService;
import com.hhplus.concert.api.concert.application.ConcertService;
import com.hhplus.concert.api.reservation.domain.entity.PaymentHistory;
import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import com.hhplus.concert.api.reservation.domain.repository.PaymentHistoryRepository;
import com.hhplus.concert.api.reservation.domain.repository.ReservationRepository;
import com.hhplus.concert.api.reservation.domain.repository.ReservationSeatRepository;
import com.hhplus.concert.api.reservation.domain.type.PaymentStatus;
import com.hhplus.concert.api.reservation.domain.type.ReservationStatus;
import com.hhplus.concert.exception.list.CustomBadRequestException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationSeatRepository reservationSeatRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final TokenService tokenService;
    private final ConcertService concertService;
    private final BalanceService balanceService;


    @Transactional
    public Reservation postReservationSeat(String uuid, Reservation reservation) {
        List<Long> seatIds = reservation.getReservationSeats().stream().map(i -> i.getSeatId()).collect(
            Collectors.toList());
        reservation.updateReservation(concertService.getConcertTitle(reservation.getConcertId()),
                                      ReservationStatus.STANDBY,
                                      LocalDateTime.now().plusMinutes(10),
                                      concertService.getSeatTotalPrice(seatIds));
        reservationRepository.save(reservation);
        List<ReservationSeat> reservationSeats = reservationSeatRepository.findAllBySeatIdInAndScheduleIdAndConcertId(seatIds,
                                                                                                      reservation.getScheduleId(),
                                                                                                      reservation.getConcertId());
        if (reservationSeats.size() != 0) {
            throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "이미 예약된 좌석입니다.");
        }
        for (ReservationSeat item : reservation.getReservationSeats()) {
            ReservationSeat seat = new ReservationSeat(item.getSeatId(), item.getScheduleId(), item.getConcertId(),
                                                       reservation.getReservationId());
            reservationSeats.add(seat);
        }
        reservationSeatRepository.saveAll(reservationSeats);

        concertService.seatStatusUpdate(seatIds, SeatStatus.IMPOSSIBLE, reservation.getUserId());
        return reservation;
    }

    @Transactional
    public PaymentHistory postPayment(String uuid, PaymentHistory paymentHistory) {
        reservationRepository.findByReservationIdAndUserId(
            paymentHistory.getReservationId(), paymentHistory.getUserId()).orElseThrow(() -> {
            throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "예약 정보가 없습니다.");
        });


        paymentHistory.updatePaymentStatus(PaymentStatus.SUCCESS, LocalDateTime.now());
        paymentHistoryRepository.save(paymentHistory);
        balanceService.useBalance(paymentHistory.getUserId(), paymentHistory.getAmount());
        tokenService.tokenExpired(uuid);

        return paymentHistory;
    }

    @Transactional
    public void reservationExpiredCheck() {
        List<Reservation> expiredReservation = reservationRepository.findAllByReservationStatusAndReservationExpiryBefore(ReservationStatus.STANDBY, LocalDateTime.now());
        if (expiredReservation.size() == 0) return;
        List<Long> seats = new ArrayList<>();
        for (Reservation reservation : expiredReservation) {
            reservation.updateReservationStatus(ReservationStatus.CANCEL);
            seats.addAll(reservation.getReservationSeats().stream().map(ReservationSeat::getSeatId).toList());
        }
        concertService.seatStatusUpdate(seats, SeatStatus.AVAILABLE, null);
        reservationSeatRepository.deleteAllById(seats);
    }
}
