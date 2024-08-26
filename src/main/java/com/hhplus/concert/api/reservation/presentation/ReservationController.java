package com.hhplus.concert.api.reservation.presentation;

import com.hhplus.concert.api.reservation.presentation.dto.request.PaymentReqDTO;
import com.hhplus.concert.api.reservation.presentation.dto.request.ReservationReqDTO;
import com.hhplus.concert.api.reservation.presentation.dto.response.PaymentResDTO;
import com.hhplus.concert.api.reservation.presentation.dto.response.ReservationResDTO;
import com.hhplus.concert.api.reservation.application.ReservationService;
import com.hhplus.concert.api.reservation.presentation.mapper.ReservationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="ReservationController", description = "좌석 예약 요청을 제공하기 위한 API")
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 좌석 예약 요청 API
    @Operation(summary = "좌석 예약 요청 API")
    @PostMapping("/seats")
    public ResponseEntity<ReservationResDTO> postReservationSeat(@RequestHeader Long userId, @RequestBody ReservationReqDTO reqVo) {
        return ResponseEntity.ok(ReservationMapper.toDto(reservationService.postReservationSeat(ReservationMapper.toEntity(reqVo))));
    }

    // 결제 API
    @Operation(summary = "결제 API")
    @PostMapping("/payments")
    public ResponseEntity<PaymentResDTO> postPayment(@RequestHeader Long userId, @RequestBody PaymentReqDTO reqVo) {
        return ResponseEntity.ok(ReservationMapper.toDtoFromPayment(reservationService.postPayment(userId, ReservationMapper.toEntityFromPayment(reqVo))));
    }
}
