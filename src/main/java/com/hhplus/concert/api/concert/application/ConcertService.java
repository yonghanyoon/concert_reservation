package com.hhplus.concert.api.concert.application;

import com.hhplus.concert.api.auth.application.AuthService;
import com.hhplus.concert.api.concert.domain.entity.Concert;
import com.hhplus.concert.api.concert.domain.entity.Schedule;
import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.domain.repository.JpaConcertRepository;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import com.hhplus.concert.api.concert.domain.repository.JpaScheduleRepository;
import com.hhplus.concert.api.concert.domain.repository.JpaSeatRepository;
import com.hhplus.concert.exception.list.CustomNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConcertService {
    private final JpaConcertRepository jpaConcertRepository;
    private final JpaScheduleRepository jpaScheduleRepository;
    private final JpaSeatRepository jpaSeatRepository;
    private final AuthService authService;

    public ConcertService(JpaConcertRepository jpaConcertRepository, JpaScheduleRepository jpaScheduleRepository,
        JpaSeatRepository jpaSeatRepository, AuthService authService) {
        this.jpaConcertRepository = jpaConcertRepository;
        this.jpaScheduleRepository = jpaScheduleRepository;
        this.jpaSeatRepository = jpaSeatRepository;
        this.authService = authService;
    }

    @Transactional
    public List<Concert> getConcerts(String uuid) {
        authService.tokenStatusCheck(uuid);

        List<Concert> concerts = jpaConcertRepository.findAll();
        if (concerts.size() == 0) {
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "콘서트가 없습니다.");
        }
        return concerts;
    }

    @Transactional
    public List<Schedule> getSchedules(String uuid, Long concertId) {
        authService.tokenStatusCheck(uuid);

        List<Schedule> schedules = jpaScheduleRepository.findByConcertIdAndScheduleDateAfter(concertId, LocalDateTime.now());
        if (schedules.size() == 0) {
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "예약 가능한 일정이 없습니다.");
        }
        return schedules;
    }

    @Transactional
    public List<Seat> getSeats(String uuid, Long scheduleId) {
        authService.tokenStatusCheck(uuid);

        List<Seat> seats = jpaSeatRepository.findByScheduleIdAndSeatStatus(scheduleId, SeatStatus.AVAILABLE);
        if (seats.size() == 0) {
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "예약 가능한 좌석이 없습니다.");
        }
        return seats;
    }

    public String getConcertTitle(Long concertId) {
        Concert concert = jpaConcertRepository.findById(concertId).orElseThrow(() -> {
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "콘서트가 없습니다.");
        });
        return concert.getTitle();
    }

    public Long getSeatTotalPrice(List<Long> seatIds) {
        List<Seat> seats = jpaSeatRepository.findAllById(seatIds);
        Long seatTotalPrice = 0L;
        for (Seat item : seats) {
            seatTotalPrice += item.getPrice();
        }
        return seatTotalPrice;
    }

    public void seatStatusImpossible(List<Long> seatIds) {
        List<Seat> seats = jpaSeatRepository.findAllById(seatIds);
        for (Seat seat : seats) {
            seat.setSeatStatus(SeatStatus.IMPOSSIBLE);
        }
        jpaSeatRepository.saveAll(seats);
    }

}
