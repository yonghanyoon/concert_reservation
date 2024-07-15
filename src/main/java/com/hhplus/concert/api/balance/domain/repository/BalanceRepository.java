package com.hhplus.concert.api.balance.domain.repository;

import com.hhplus.concert.api.balance.domain.entity.Balance;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findByUserId(Long userId);
}
