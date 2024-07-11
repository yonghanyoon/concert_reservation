package com.hhplus.concert.api.balance.domain;

import com.hhplus.concert.api.balance.domain.Balance;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findByUserId(Long userId);
}
