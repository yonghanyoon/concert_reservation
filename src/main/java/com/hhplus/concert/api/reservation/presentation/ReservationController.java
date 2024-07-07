package com.hhplus.concert.api.reservation.presentation;

import com.hhplus.concert.api.reservation.domain.Schedule;
import com.hhplus.concert.api.reservation.domain.Seat;
import com.hhplus.concert.api.reservation.presentation.dto.request.SeatReqVo;
import com.hhplus.concert.api.reservation.presentation.dto.response.ReservationSeatResVo;
import com.hhplus.concert.api.reservation.presentation.dto.response.ScheduleResVo;
import com.hhplus.concert.api.reservation.presentation.dto.response.SeatResVo;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {


    // 예약 가능 날짜 조회 API
    @GetMapping("/{contentId}")
    public ResponseEntity<ScheduleResVo> getSchedule(HttpServletRequest request, @PathVariable Long contentId) {
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule(1L, contentId, LocalDateTime.now()));
        scheduleList.add(new Schedule(2L, contentId, LocalDateTime.now().plusDays(5)));
        return ResponseEntity.ok(new ScheduleResVo(scheduleList));
    }

    // 해당 날짜 예약 가능 좌석 조회 API
    @GetMapping("/seats/{scheduleId}")
    public ResponseEntity<SeatResVo> getSeat(HttpServletRequest request, @PathVariable Long scheduleId) {
        List<Seat> seatList = new ArrayList<>();
        seatList.add(new Seat(1L, 1L, scheduleId, 3000L));
        seatList.add(new Seat(2L, 2L, scheduleId, 3000L));
        return ResponseEntity.ok(new SeatResVo(seatList));
    }

    // 좌석 예약 요청 API
    @PostMapping("/seats")
    public ResponseEntity<ReservationSeatResVo> postReservationSeat(HttpServletRequest request, SeatReqVo reqVo) {
        List<Long> seatIdList = new ArrayList<>();
        seatIdList.add(1L);
        seatIdList.add(2L);
        return ResponseEntity.ok(new ReservationSeatResVo(1L, seatIdList, 6000L, LocalDateTime.now().plusMinutes(5)));
    }
}
