package com.hhplus.concert.api.balance.application;

import com.hhplus.concert.api.balance.domain.entity.Balance;
import com.hhplus.concert.api.balance.domain.repository.BalanceRepository;
import com.hhplus.concert.exception.list.CustomBadRequestException;
import com.hhplus.concert.exception.list.CustomNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository jpaBalanceRepository;

    @Transactional
    public Balance putCharge(Balance balance) {
        Balance selectBalance = jpaBalanceRepository.findByUserId(balance.getUserId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자"));
        balance.putCharge(selectBalance.getBalanceId(), selectBalance.getAmount() + balance.getAmount(), LocalDateTime.now());
        return jpaBalanceRepository.save(balance);
    }

    @Transactional(readOnly = true)
    public Balance getBalance(Long userId) {
        return jpaBalanceRepository.findByUserId(userId).orElseThrow(() -> new CustomNotFoundException(
            HttpStatus.NOT_FOUND, "존재하지 않는 사용자"));
    }

    public void useBalance(Long userId, Long amount) {
        Balance balance = jpaBalanceRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자"));
        if (balance.getAmount() < amount) {
            throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "잔액이 부족합니다.");
        }
        balance.useAmount(balance.getAmount() - amount, LocalDateTime.now());
    }
}
