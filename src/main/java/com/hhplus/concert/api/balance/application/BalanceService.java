package com.hhplus.concert.api.balance.application;

import com.hhplus.concert.api.balance.domain.Balance;
import com.hhplus.concert.api.balance.domain.BalanceRepository;
import com.hhplus.concert.exception.list.CustomNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Transactional
    public Balance putCharge(Balance balance) {
        Balance selectBalance = balanceRepository.findByUserId(balance.getUserId()).orElseThrow(() -> new CustomNotFoundException(
            HttpStatus.NOT_FOUND, "존재하지 않는 사용자"));
        balance.setBalanceId(selectBalance.getBalanceId());
        balance.setAmount(selectBalance.getAmount() + balance.getAmount());
        balance.setModDt(LocalDateTime.now());
        return balanceRepository.save(balance);
    }

    @Transactional(readOnly = true)
    public Balance getBalance(Long userId) {
        return balanceRepository.findByUserId(userId).orElseThrow(() -> new CustomNotFoundException(
            HttpStatus.NOT_FOUND, "존재하지 않는 사용자"));
    }
}
