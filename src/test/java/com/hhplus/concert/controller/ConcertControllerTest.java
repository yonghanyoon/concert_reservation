package com.hhplus.concert.controller;

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
public class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ConcertService concertService;

    @DisplayName("예약 가능 일정 조회 테스트")
    @Test
    public void getSchedulesTest() throws Exception {
        // given
        Long concertId = 1L;
        String uuid = "011b60f5-dfd9-4975-9a23-1ef9953c0c22";
        List<Schedule> schedules = new ArrayList<>();
        Schedule schedule = new Schedule();
        schedule.setScheduleId(1L);
        schedule.setConcertId(1L);
        schedule.setScheduleDate(LocalDateTime.of(2024, 6, 28, 12, 0));
        schedule.setTotalSeat(50L);
        schedules.add(schedule);

        // when
        when(concertService.getSchedules(uuid, concertId)).thenReturn(schedules);

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
        String uuid = "011b60f5-dfd9-4975-9a23-1ef9953c0c22";
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat();
        seat.setSeatId(1L);
        seat.setSeatStatus(SeatStatus.AVAILABLE);
        seat.setSeatNumber(1L);
        seat.setScheduleId(1L);
        seat.setPrice(60000L);
        seats.add(seat);

        // when
        when(concertService.getSeats(uuid, scheduleId)).thenReturn(seats);

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
