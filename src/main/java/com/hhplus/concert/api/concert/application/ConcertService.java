package com.hhplus.concert.api.concert.application;

import com.hhplus.concert.api.concert.domain.entity.Concert;
import com.hhplus.concert.api.concert.domain.entity.Schedule;
import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.domain.repository.ConcertRepository;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import com.hhplus.concert.api.concert.domain.repository.ScheduleRepository;
import com.hhplus.concert.api.concert.domain.repository.SeatRepository;
import com.hhplus.concert.common.exception.list.CustomBadRequestException;
import com.hhplus.concert.common.exception.list.CustomNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConcertService {
    private final ConcertRepository concertRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public List<Concert> getConcerts() {
        List<Concert> concerts = concertRepository.findAll();
        if (concerts.size() == 0) {
            log.info("[콘서트 조회] -> 콘서트가 없습니다.");
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "콘서트가 없습니다.");
        }
        return concerts;
    }

    @Transactional
    public List<Schedule> getSchedules(Long concertId) {
        List<Schedule> schedules = scheduleRepository.findByConcertIdAndScheduleDateAfter(concertId, LocalDateTime.now());
        if (schedules.size() == 0) {
            log.info(String.format("[예약 가능 일정 조회] concertId : %d -> 예약 가능한 일정이 없습니다.", concertId));
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "예약 가능한 일정이 없습니다.");
        }
        return schedules;
    }

    @Transactional
    public List<Seat> getSeats(Long scheduleId) {
        List<Seat> seats = seatRepository.findByScheduleIdAndSeatStatus(scheduleId, SeatStatus.AVAILABLE);
        if (seats.size() == 0) {
            log.info(String.format("[예약 가능 좌석 조회] scheduleId : %d -> 예약 가능한 좌석이 없습니다.", scheduleId));
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "예약 가능한 좌석이 없습니다.");
        }
        return seats;
    }

    public String getConcertTitle(Long concertId) {
        Concert concert = concertRepository.findById(concertId).orElseThrow(() -> {
            log.error(String.format("[예약 정보 콘서트 제목 조회] concertId : %d -> 콘서트가 없습니다.", concertId));
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "콘서트가 없습니다.");
        });
        return concert.getTitle();
    }

    public Long getSeatTotalPrice(List<Long> seatIds) {
        List<Seat> seats = seatRepository.findAllById(seatIds);
        Long seatTotalPrice = 0L;
        for (Seat item : seats) {
            seatTotalPrice += item.getPrice();
        }
        return seatTotalPrice;
    }

    public void seatStatusUpdate(List<Long> seatIds, SeatStatus seatStatus, Long userId) {
        List<Seat> seats = seatRepository.findAllById(seatIds);
        for (Seat seat : seats) {
            seat.updateSeatStatus(seatStatus, userId);
        }
    }

    public void optimisticSeat(List<Long> seatIds, Long userId) {
        try {
            List<Seat> seats = seatRepository.findAllBySeatIdAndSeatStatus(seatIds, SeatStatus.AVAILABLE);
            if (seats.size() == 0) {
                log.info("[좌석 예약] 이미 예약된 좌석입니다.");
                throw new EntityNotFoundException("이미 예약된 좌석입니다.");
            }
            for (Seat seat : seats) {
                seat.updateSeatStatus(SeatStatus.IMPOSSIBLE, userId);
            }
            seatRepository.saveAll(seats);
        } catch (OptimisticLockingFailureException | OptimisticLockException e) {
            log.warn("[좌석 예약] 낙관적 락 충돌 발생");
            throw new OptimisticLockException("OptimisticLock 충돌");
        }
    }

}
