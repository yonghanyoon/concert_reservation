package com.hhplus.concert.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.hhplus.concert.api.concert.application.ConcertService;
import com.hhplus.concert.api.concert.domain.entity.Concert;
import com.hhplus.concert.api.concert.domain.entity.Schedule;
import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ConcertServiceUnitTest {

    private ConcertService concertService = Mockito.mock(ConcertService.class);

    @DisplayName("콘서트 조회 테스트")
    @Test
    public void getConcertsTest() {
        // given
        List<Concert> concerts = new ArrayList<>();
        Concert concert = new Concert(1L, "콘서트 테스트");
        concerts.add(concert);

        // when
        when(concertService.getConcerts()).thenReturn(concerts);

        // then
        assertThat(concertService.getConcerts().get(0).getConcertId()).isEqualTo(1L);
        assertThat(concertService.getConcerts().get(0).getTitle()).isEqualTo("콘서트 테스트");
    }

    @DisplayName("예약 가능 일정 조회 테스트")
    @Test
    public void getSchedulesTest() throws Exception {
        // given
        Long concertId = 1L;
        List<Schedule> schedules = new ArrayList<>();
        Schedule schedule = new Schedule(1L, concertId, LocalDateTime.of(2024, 6, 28, 12, 0), 50L);
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
        Seat seat = Seat.builder()
                        .seatId(1L)
                        .seatStatus(SeatStatus.AVAILABLE)
                        .seatNumber(1L)
                        .scheduleId(scheduleId)
                        .price(60000L)
                        .build();
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

    @DisplayName("콘서트 제목 조회 테스트")
    @Test
    void get_concert_title_test() {
        // given
        Long concertId = 1L;
        String title = "발라드 황제 이석범 콘서트";
        // when
        when(concertService.getConcertTitle(concertId)).thenReturn(title);
        // then
        assertThat(concertService.getConcertTitle(concertId)).isEqualTo(title);
    }

    @DisplayName("예약 좌석 총 금액 테스트")
    @Test
    void get_seat_total_price_test() {
        // given
        List<Long> seatIds = List.of(1L, 2L, 3L);
        // when
        when(concertService.getSeatTotalPrice(seatIds)).thenReturn(180000L);
        // then
        assertThat(concertService.getSeatTotalPrice(seatIds)).isEqualTo(180000L);
    }
}
