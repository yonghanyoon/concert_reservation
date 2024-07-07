package com.hhplus.concert.api.balance.presentation;

import com.hhplus.concert.api.balance.presentation.dto.request.ChargeReqVo;
import com.hhplus.concert.api.balance.presentation.dto.response.UserBalanceResVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

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
}
