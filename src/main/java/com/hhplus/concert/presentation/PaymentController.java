package com.hhplus.concert.presentation;

import com.hhplus.concert.presentation.dto.request.ChargeReqVo;
import com.hhplus.concert.presentation.dto.request.PaymentReqVo;
import com.hhplus.concert.presentation.dto.response.PaymentResVo;
import com.hhplus.concert.presentation.dto.response.UserBalanceResVo;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    // 잔액 충전 API
    @PatchMapping("/charge")
    public ResponseEntity<UserBalanceResVo> postCharge(HttpServletRequest request, ChargeReqVo reqVo) {
        return ResponseEntity.ok(new UserBalanceResVo(1L, 10000L));
    }

    // 잔액 조회 API
    @GetMapping("/{userId}")
    public ResponseEntity<UserBalanceResVo> getBalance(HttpServletRequest request, @PathVariable Long userId) {
        return ResponseEntity.ok(new UserBalanceResVo(userId, 10000L));
    }

    // 결제 API
    @PostMapping("")
    public ResponseEntity<PaymentResVo> postPayment(HttpServletRequest request, PaymentReqVo reqVo) {
        return ResponseEntity.ok(new PaymentResVo(1L, LocalDateTime.now()));
    }
}
