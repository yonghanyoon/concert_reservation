package com.hhplus.concert.api.reservation.application;

import com.hhplus.concert.api.balance.application.BalanceService;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import com.hhplus.concert.api.reservation.domain.entity.ReservationCreatedEvent;
import com.hhplus.concert.api.reservation.domain.event.ReservationEvent;
import com.hhplus.concert.api.reservation.domain.repository.ReservationCreatedEventRepository;
import com.hhplus.concert.api.reservation.domain.type.ReservationEventStatus;
import com.hhplus.concert.api.reservation.infrastructure.SlackApiClient;
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
import com.hhplus.concert.common.exception.list.CustomNotFoundException;
import com.hhplus.concert.config.RedisLock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;
    private final ReservationCreatedEventRepository reservationCreatedEventRepository;
    private final SlackApiClient slackApiClient;

//    @CacheEvict(value = "seats: ", key = "#reservation.scheduleId")
    @Transactional
    public Reservation postReservationSeat(Reservation reservation) {
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
        String uuid = String.valueOf(UUID.randomUUID());
        eventPublisher.publishEvent(new ReservationEvent(uuid, reservation));
        return reservation;
    }

    @RedisLock(key = "'paymentLock:' + #paymentHistory.reservationId", timeout = 5000)
    public PaymentHistory postPayment(Long userId, PaymentHistory paymentHistory) {
        reservationRepository.findByReservationIdAndUserId(
            paymentHistory.getReservationId(), paymentHistory.getUserId()).orElseThrow(() -> {
                log.warn(String.format("[결제 히스토리] reservationId : %d -> 예약 정보가 없습니다.", paymentHistory.getReservationId()));
                throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "예약 정보가 없습니다.");
            });

        balanceService.useBalance(paymentHistory.getUserId(), paymentHistory.getAmount());
        paymentHistory.updatePaymentStatus(PaymentStatus.SUCCESS, LocalDateTime.now());
        paymentHistoryRepository.save(paymentHistory);
        tokenService.tokenExpired(userId);

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
            concertService.seatStatusUpdate(reservation.getScheduleId(), reservation.getReservationSeats().stream().map(ReservationSeat::getSeatId).toList(), SeatStatus.AVAILABLE, null);
            reservationSeats.addAll(reservation.getReservationSeats());
        }
        for (ReservationSeat reservationSeat : reservationSeats) {
            reservationSeat.updateReservationStatus(ReservationStatus.CANCEL);
        }
    }

    @Transactional
    public void processReservationEvent() {
        List<ReservationCreatedEvent> reservationEventList = reservationCreatedEventRepository.findAllByReservationEventStatusAndCreatedAtBefore(ReservationEventStatus.INIT, LocalDateTime.now().minusMinutes(3));
        if (reservationEventList.size() > 0) {
            reservationEventList.stream().forEach(i -> {
                if (i.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(10))) {
                    slackApiClient.sendMessageToDeveloper(i.getReservationId());
                    i.updateStatus(ReservationEventStatus.FAILED);
                    return;
                }
                Reservation reservation = reservationRepository.findById(i.getReservationId()).orElseThrow(() -> {
                    throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "예약 정보가 없습니다.");
                });
                eventPublisher.publishEvent(new ReservationEvent(i.getReservationCreatedEventId(), reservation));
            });
        }
    }

    @Transactional
    public void reservationEventUpdate(ReservationEvent event) {
        ReservationCreatedEvent reservationCreatedEvent = reservationCreatedEventRepository.findById(event.getUuid()).orElseThrow(() -> {
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "존재하지 않는 이벤트");
        });
        reservationCreatedEvent.updateStatus(ReservationEventStatus.PUBLISHED);
    }
}
