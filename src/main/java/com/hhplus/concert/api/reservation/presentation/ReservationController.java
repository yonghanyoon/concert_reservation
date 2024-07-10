package com.hhplus.concert.api.reservation.presentation;

import com.hhplus.concert.api.reservation.application.ReservationService;
import com.hhplus.concert.api.reservation.presentation.dto.request.ReservationReqVo;
import com.hhplus.concert.api.reservation.presentation.dto.response.ReservationResVo;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 좌석 예약 요청 API
    @PostMapping("/seats")
    public ResponseEntity<ReservationResVo> postReservationSeat(HttpServletRequest request, ReservationReqVo reqVo) {
        List<Long> seatIdList = new ArrayList<>();
        seatIdList.add(1L);
        seatIdList.add(2L);
        return ResponseEntity.ok(new ReservationResVo(1L, seatIdList, 6000L, LocalDateTime.now().plusMinutes(5)));
    }
}
