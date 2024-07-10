package com.hhplus.concert.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.hhplus.concert.api.concert.application.ConcertService;
import com.hhplus.concert.api.concert.domain.entity.Schedule;
import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ConcertServiceTest {

    private ConcertService concertService = Mockito.mock(ConcertService.class);

    @DisplayName("예약 가능 일정 조회 테스트")
    @Test
    public void getSchedulesTest() throws Exception {
        // given
        Long concertId = 1L;
        List<Schedule> schedules = new ArrayList<>();
        Schedule schedule = new Schedule();
        schedule.setScheduleId(1L);
        schedule.setConcertId(concertId);
        schedule.setScheduleDate(LocalDateTime.of(2024, 6, 28, 12, 0));
        schedule.setTotalSeat(50L);
        schedules.add(schedule);

        // when
        when(concertService.getSchedules(concertId)).thenReturn(schedules);

        // then
        assertThat(concertService.getSchedules(concertId).get(0).getScheduleId()).isEqualTo(1L);
        assertThat(concertService.getSchedules(concertId).get(0).getConcertId()).isEqualTo(concertId);
        assertThat(concertService.getSchedules(concertId).get(0).getScheduleDate()).isEqualTo("2024-06-28T12:00:00");
        assertThat(concertService.getSchedules(concertId).get(0).getTotalSeat()).isEqualTo(50L);
    }

    @DisplayName("예약 가능 좌석 조회 테스트")
    @Test
    public void getSeatsTest() throws Exception {
        // given
        Long scheduleId = 1L;
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat();
        seat.setSeatId(1L);
        seat.setSeatStatus(SeatStatus.AVAILABLE);
        seat.setSeatNumber(1L);
        seat.setScheduleId(scheduleId);
        seat.setPrice(60000L);
        seats.add(seat);

        // when
        when(concertService.getSeats(scheduleId)).thenReturn(seats);

        // then
        assertThat(concertService.getSeats(scheduleId).get(0).getSeatId()).isEqualTo(1L);
        assertThat(concertService.getSeats(scheduleId).get(0).getSeatStatus()).isEqualTo(SeatStatus.AVAILABLE);
        assertThat(concertService.getSeats(scheduleId).get(0).getSeatNumber()).isEqualTo(1L);
        assertThat(concertService.getSeats(scheduleId).get(0).getScheduleId()).isEqualTo(scheduleId);
        assertThat(concertService.getSeats(scheduleId).get(0).getPrice()).isEqualTo(60000L);
    }
}
