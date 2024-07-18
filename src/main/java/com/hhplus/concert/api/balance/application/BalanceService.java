package com.hhplus.concert.api.balance.application;

import com.hhplus.concert.api.balance.domain.entity.Balance;
import com.hhplus.concert.api.balance.domain.repository.BalanceRepository;
import com.hhplus.concert.common.exception.list.CustomBadRequestException;
import com.hhplus.concert.common.exception.list.CustomNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceService {

    private final BalanceRepository balanceRepository;

    @Transactional
    public Balance putCharge(Balance balance) {
        Balance selectBalance = balanceRepository.findByUserId(balance.getUserId()).orElseThrow(() -> {
            log.warn(String.format("[잔액 충전] userId : %d -> 존재하지 않는 사용자", balance.getUserId()));
            throw new EntityNotFoundException("존재하지 않는 사용자");
        });
        balance.putCharge(selectBalance.getBalanceId(), balance.getAmount(), LocalDateTime.now());
        return balanceRepository.save(balance);
    }

    @Transactional(readOnly = true)
    public Balance getBalance(Long userId) {
        return balanceRepository.findByUserId(userId).orElseThrow(() -> {
            log.warn(String.format("[잔액 조회] userId : %d -> 존재하지 않는 사용자", userId));
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자");
        });
    }

    public void useBalance(Long userId, Long amount) {
        Balance balance = balanceRepository.findByUserId(userId).orElseThrow(() -> {
            log.warn(String.format("[결제 잔액 차감] userId : %d -> 존재하지 않는 사용자", userId));
            throw new EntityNotFoundException("존재하지 않는 사용자");
        });
        if (balance.getAmount() < amount) {
            log.warn(String.format("[결제 잔액 차감] 현재 잔액 : %d -> 잔액이 부족합니다.", balance.getAmount()));
            throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "잔액이 부족합니다.");
        }
        balance.useAmount(amount, LocalDateTime.now());
    }
}
