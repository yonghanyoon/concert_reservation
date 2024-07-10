package com.hhplus.concert.api.concert.application;

import com.hhplus.concert.api.concert.domain.entity.Schedule;
import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.domain.repository.ScheduleRepository;
import com.hhplus.concert.api.concert.domain.repository.SeatRepository;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import com.hhplus.concert.exception.list.CustomNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConcertService {
    private final ScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;

    public ConcertService(ScheduleRepository scheduleRepository, SeatRepository seatRepository) {
        this.scheduleRepository = scheduleRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional(readOnly = true)
    public List<Schedule> getSchedules(Long concertId) {
        List<Schedule> schedules = scheduleRepository.findByConcertIdAndScheduleDateAfter(concertId, LocalDateTime.now());
        if (schedules.size() == 0) {
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "예약 가능한 일정이 없습니다.");
        }
        return schedules;
    }

    @Transactional(readOnly = true)
    public List<Seat> getSeats(Long scheduleId) {
        List<Seat> seats = seatRepository.findByScheduleIdAndSeatStatus(scheduleId, SeatStatus.AVAILABLE);
        if (seats.size() == 0) {
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "예약 가능한 좌석이 없습니다.");
        }
        return seats;
    }

}
