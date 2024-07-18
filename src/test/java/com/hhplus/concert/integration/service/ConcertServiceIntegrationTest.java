package com.hhplus.concert.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.concert.api.concert.application.ConcertService;
import com.hhplus.concert.api.concert.domain.entity.Concert;
import com.hhplus.concert.api.concert.domain.entity.Schedule;
import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import com.hhplus.concert.common.exception.list.CustomNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class ConcertServiceIntegrationTest {

    @Autowired
    private ConcertService concertService;

    @DisplayName("콘서트 조회 테스트")
    @Test
    void get_concerts_test() {
        // given
        // when
        List<Concert> concerts = concertService.getConcerts();
        // then
        assertEquals(concerts.get(0).getConcertId(), 1L);
        assertEquals(concerts.get(0).getTitle(), "발라드 황제 이석범 콘서트");
    }

    @DisplayName("예약 가능 일정 조회 테스트")
    @Test
    void get_schedules_test() {
        // given
        Long concertId = 1L;
        // when
        List<Schedule> schedules = concertService.getSchedules(concertId);
        // then
        assertEquals(schedules.get(0).getConcertId(), concertId);
        assertEquals(schedules.get(0).getScheduleId(), 1L);
        assertEquals(schedules.get(0).getScheduleDate(), LocalDateTime.of(2024, 7, 18, 07, 50, 57));
        assertEquals(schedules.get(0).getTotalSeat(), 50L);
    }

    @DisplayName("예약 가능 일정 조회 0건 실패 테스트")
    @Test
    void get_schedules_fail_test() {
        // given
        Long concertId = 99L;
        // when
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> concertService.getSchedules(concertId));
        // then
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
        assertEquals("예약 가능한 일정이 없습니다.", exception.getMessage());
    }

    @DisplayName("예약 가능 좌석 조회 테스트")
    @Test
    void get_seats_test() {
        // given
        Long scheduleId = 1L;
        // when
        List<Seat> seats = concertService.getSeats(scheduleId);
        // then
        assertEquals(seats.get(0).getSeatId(), 1L);
        assertEquals(seats.get(0).getSeatStatus(), SeatStatus.AVAILABLE);
        assertEquals(seats.get(0).getSeatNumber(), 1L);
        assertEquals(seats.get(0).getScheduleId(), scheduleId);
        assertEquals(seats.get(0).getPrice(), 60000L);
    }

    @DisplayName("예약 가능 좌석 조회 0건 실패 테스트")
    @Test
    void get_seats_fail_test() {
        // given
        Long scheduleId = 99L;
        // when
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> concertService.getSeats(scheduleId));
        // then
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
        assertEquals("예약 가능한 좌석이 없습니다.", exception.getMessage());
    }
}
