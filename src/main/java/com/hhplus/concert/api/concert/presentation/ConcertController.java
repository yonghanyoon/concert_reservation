package com.hhplus.concert.api.concert.presentation;

import com.hhplus.concert.api.concert.application.ConcertService;
import com.hhplus.concert.api.concert.presentation.dto.response.ScheduleResVo;
import com.hhplus.concert.api.concert.presentation.dto.response.SeatResVo;
import com.hhplus.concert.api.concert.presentation.mapper.ConcertMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    // 예약 가능 날짜 조회 API
    @GetMapping("/schedules/{contentId}")
    public ResponseEntity<List<ScheduleResVo>> getSchedules(HttpServletRequest request, @PathVariable Long contentId) {
        return ResponseEntity.ok(ConcertMapper.toDtoFromSchedule(concertService.getSchedules(contentId)));
    }

    // 해당 날짜 예약 가능 좌석 조회 API
    @GetMapping("/seats/{scheduleId}")
    public ResponseEntity<List<SeatResVo>> getSeats(HttpServletRequest request, @PathVariable Long scheduleId) {
        return ResponseEntity.ok(ConcertMapper.toDtoFromSeat(concertService.getSeats(scheduleId)));
    }
}
