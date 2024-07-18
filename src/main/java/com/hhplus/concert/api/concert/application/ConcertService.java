package com.hhplus.concert.api.concert.application;

import com.hhplus.concert.api.concert.domain.entity.Concert;
import com.hhplus.concert.api.concert.domain.entity.Schedule;
import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.domain.repository.ConcertRepository;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import com.hhplus.concert.api.concert.domain.repository.ScheduleRepository;
import com.hhplus.concert.api.concert.domain.repository.SeatRepository;
import com.hhplus.concert.common.exception.list.CustomNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public List<Concert> getConcerts() {
        List<Concert> concerts = concertRepository.findAll();
        if (concerts.size() == 0) {
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "콘서트가 없습니다.");
        }
        return concerts;
    }

    @Transactional
    public List<Schedule> getSchedules(Long concertId) {
        List<Schedule> schedules = scheduleRepository.findByConcertIdAndScheduleDateAfter(concertId, LocalDateTime.now());
        if (schedules.size() == 0) {
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "예약 가능한 일정이 없습니다.");
        }
        return schedules;
    }

    @Transactional
    public List<Seat> getSeats(Long scheduleId) {
        List<Seat> seats = seatRepository.findByScheduleIdAndSeatStatus(scheduleId, SeatStatus.AVAILABLE);
        if (seats.size() == 0) {
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "예약 가능한 좌석이 없습니다.");
        }
        return seats;
    }

    public String getConcertTitle(Long concertId) {
        Concert concert = concertRepository.findById(concertId).orElseThrow(() -> {
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

}
