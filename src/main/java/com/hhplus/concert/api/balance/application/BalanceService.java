package com.hhplus.concert.api.balance.application;

import com.hhplus.concert.api.balance.domain.Balance;
import com.hhplus.concert.api.balance.domain.JpaBalanceRepository;
import com.hhplus.concert.exception.list.CustomNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final JpaBalanceRepository jpaBalanceRepository;

    @Transactional
    public Balance putCharge(Balance balance) {
        Balance selectBalance = jpaBalanceRepository.findByUserId(balance.getUserId()).orElseThrow(() -> new CustomNotFoundException(
            HttpStatus.NOT_FOUND, "존재하지 않는 사용자"));
        balance.setBalanceId(selectBalance.getBalanceId());
        balance.setAmount(selectBalance.getAmount() + balance.getAmount());
        balance.setModDt(LocalDateTime.now());
        return jpaBalanceRepository.save(balance);
    }

    @Transactional(readOnly = true)
    public Balance getBalance(Long userId) {
        return jpaBalanceRepository.findByUserId(userId).orElseThrow(() -> new CustomNotFoundException(
            HttpStatus.NOT_FOUND, "존재하지 않는 사용자"));
    }
}
