package com.hhplus.concert.api.balance.presentation;

import com.hhplus.concert.api.balance.application.BalanceService;
import com.hhplus.concert.api.balance.presentation.dto.request.ChargeReqVo;
import com.hhplus.concert.api.balance.presentation.dto.response.BalanceResVo;
import com.hhplus.concert.api.balance.presentation.mapper.BalanceMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    // 잔액 충전 API
    @PutMapping("/charge")
    public ResponseEntity<BalanceResVo> putCharge(HttpServletRequest request, @Valid @RequestBody ChargeReqVo reqVo) {
        return ResponseEntity.ok(BalanceMapper.toDto(balanceService.putCharge(BalanceMapper.toEntity(reqVo))));
    }

    // 잔액 조회 API
    @GetMapping("/{userId}")
    public ResponseEntity<BalanceResVo> getBalance(HttpServletRequest request, @PathVariable Long userId) {
        return ResponseEntity.ok(BalanceMapper.toDto(balanceService.getBalance(userId)));
    }
}
