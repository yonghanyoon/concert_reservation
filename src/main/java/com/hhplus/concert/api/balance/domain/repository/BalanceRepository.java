package com.hhplus.concert.api.balance.domain.repository;

import com.hhplus.concert.api.balance.domain.entity.Balance;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<Balance> findByUserId(Long userId);
}
