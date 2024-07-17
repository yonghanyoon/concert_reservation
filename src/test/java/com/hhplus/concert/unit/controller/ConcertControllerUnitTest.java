package com.hhplus.concert.unit.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import com.hhplus.concert.api.concert.application.ConcertService;
import com.hhplus.concert.api.concert.domain.entity.Schedule;
import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import com.hhplus.concert.api.concert.presentation.ConcertController;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ConcertController.class)
public class ConcertControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ConcertService concertService;

    @DisplayName("예약 가능 일정 조회 테스트")
    @Test
    public void getSchedulesTest() throws Exception {
        // given
        Long concertId = 1L;
        List<Schedule> schedules = new ArrayList<>();
        Schedule schedule = new Schedule(1L, 1L, LocalDateTime.of(2024, 6, 28, 12, 0), 50L);
        schedules.add(schedule);

        // when
        when(concertService.getSchedules(concertId)).thenReturn(schedules);

        // then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/concerts/schedules/{contentId}", concertId))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].scheduleId").value(1L))
            .andExpect(jsonPath("$[0].concertId").value(1L))
            .andExpect(jsonPath("$[0].scheduleDate").value("2024-06-28T12:00:00"))
            .andExpect(jsonPath("$[0].totalSeat").value(50L));
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
        mockMvc.perform(
                   MockMvcRequestBuilders.get("/api/concerts/seats/{scheduleId}", scheduleId))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].seatId").value(1L))
               .andExpect(jsonPath("$[0].seatStatus").value("AVAILABLE"))
               .andExpect(jsonPath("$[0].seatNumber").value(1L))
               .andExpect(jsonPath("$[0].scheduleId").value(1L))
               .andExpect(jsonPath("$[0].price").value(60000L));
    }
}
