package com.hhplus.concert.api.concert.presentation;

import com.hhplus.concert.api.concert.application.ConcertService;
import com.hhplus.concert.api.concert.presentation.dto.response.ConcertResDTO;
import com.hhplus.concert.api.concert.presentation.dto.response.ScheduleResDTO;
import com.hhplus.concert.api.concert.presentation.dto.response.SeatResDTO;
import com.hhplus.concert.api.concert.presentation.mapper.ConcertMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ConcertController", description = "예약 가능한 콘서트 정보를 제공하기 위한 API")
@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    // 콘서트 조회 API
    @Operation(summary = "콘서트 조회 API")
    @GetMapping("")
    public ResponseEntity<List<ConcertResDTO>> getConcerts(@RequestHeader Long userId, @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ConcertMapper.toDtoFromConcert(concertService.getConcerts(page, size)));
    }

    // 예약 가능 날짜 조회 API
    @Operation(summary = "예약 가능 날짜 조회 API")
    @GetMapping("/schedules/{contentId}")
    public ResponseEntity<List<ScheduleResDTO>> getSchedules(@RequestHeader Long userId, @PathVariable Long contentId) {
        return ResponseEntity.ok(ConcertMapper.toDtoFromSchedule(concertService.getSchedules(contentId)));
    }

    // 해당 날짜 예약 가능 좌석 조회 API
    @Operation(summary = "해당 날짜 예약 가능 좌석 조회 API")
    @GetMapping("/seats/{scheduleId}")
    public ResponseEntity<List<SeatResDTO>> getSeats(@RequestHeader Long userId, @PathVariable Long scheduleId) {
        return ResponseEntity.ok(ConcertMapper.toDtoFromSeat(concertService.getSeats(scheduleId)));
    }
}
