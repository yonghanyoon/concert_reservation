package com.hhplus.concert.api.reservation.application;

import com.hhplus.concert.api.balance.application.BalanceService;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import com.hhplus.concert.api.reservation.infrastructure.redis.repository.RedisRepository;
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
import com.hhplus.concert.common.exception.list.CustomBadRequestException;
import com.hhplus.concert.common.handler.TransactionHandler;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationSeatRepository reservationSeatRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final TokenService tokenService;
    private final ConcertService concertService;
    private final BalanceService balanceService;
    private final RedisRepository redisRepository;
    private final TransactionHandler transactionHandler;
    @Value("${spring.data.redis.reservation-key}")
    private String reservationKey;
    @Value("${spring.data.redis.payment-key}")
    private String paymentKey;

    public Reservation reservationLock(Reservation reservation) {
        String ranValue = UUID.randomUUID().toString();
        if (redisRepository.addLock(reservationKey, ranValue, 5L, TimeUnit.SECONDS)) {
            log.info("[좌석 예약] Lock 획득");
            try {
                transactionHandler.runWithTransaction(this::postReservationSeat, reservation);
            } finally {
                redisRepository.delLock(reservationKey, ranValue);
                log.info("[좌석 예약] Lock 해제");
            }
        } else {
            log.warn("[좌석 예약] Lock 획득 실패");
            throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "락 획득 실패");
        }
        return reservation;
    }

    public PaymentHistory paymentLock(String uuid, PaymentHistory paymentHistory) {
        String ranValue = UUID.randomUUID().toString();
        if (redisRepository.addLock(paymentKey, ranValue, 5L, TimeUnit.SECONDS)) {
            log.info("[결제 히스토리] Lock 획득");
            try {
                transactionHandler.runWithTransaction(this::postPayment, uuid, paymentHistory);
            } finally {
                redisRepository.delLock(paymentKey, ranValue);
                log.info("[결제 히스토리] Lock 해제");
            }
        } else {
            log.warn("[결제 히스토리] Lock 획득 실패");
            throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "락 획득 실패");
        }
        return paymentHistory;
    }

    public Reservation postReservationSeat(Reservation reservation) {
        log.info("[좌석 예약] Transaction 시작");
        List<Long> seatIds = reservation.getReservationSeats().stream()
                                        .map(i -> i.getSeatId())
                                        .collect(Collectors.toList());

        concertService.redLockSeat(seatIds, reservation.getUserId());

        reservation.updateReservation(
            concertService.getConcertTitle(reservation.getConcertId()),
            ReservationStatus.STANDBY,
            LocalDateTime.now().plusMinutes(10),
            concertService.getSeatTotalPrice(seatIds)
        );
        reservationRepository.save(reservation);
        List<ReservationSeat> reservationSeats = reservation.getReservationSeats().stream()
                                                            .map(item -> ReservationSeat.builder()
                                                                                        .seatId(item.getSeatId())
                                                                                        .scheduleId(item.getScheduleId())
                                                                                        .concertId(item.getConcertId())
                                                                                        .reservationId(reservation.getReservationId())
                                                                                        .reservationStatus(ReservationStatus.STANDBY)
                                                                                        .build())
                                                            .collect(Collectors.toList());

        reservationSeatRepository.saveAll(reservationSeats);
        return reservation;
    }

    @Transactional
    public PaymentHistory postPayment(String uuid, PaymentHistory paymentHistory) {
        reservationRepository.findByReservationIdAndUserId(
            paymentHistory.getReservationId(), paymentHistory.getUserId()).orElseThrow(() -> {
                log.warn(String.format("[결제 히스토리] reservationId : %d -> 예약 정보가 없습니다.", paymentHistory.getReservationId()));
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
        List<ReservationSeat> reservationSeats = new ArrayList<>();
        for (Reservation reservation : expiredReservation) {
            reservation.updateReservationStatus(ReservationStatus.CANCEL);
            log.info(String.format("[ReservationScheduler] reservationId : %d -> 예약 시간 만료", reservation.getReservationId()));
            reservationSeats.addAll(reservation.getReservationSeats());
        }
        concertService.seatStatusUpdate(reservationSeats.stream().map(ReservationSeat::getSeatId).toList(), SeatStatus.AVAILABLE, null);
        for (ReservationSeat reservationSeat : reservationSeats) {
            reservationSeat.updateReservationStatus(ReservationStatus.CANCEL);
        }
    }
}
