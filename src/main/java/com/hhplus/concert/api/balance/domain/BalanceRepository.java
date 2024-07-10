package com.hhplus.concert.api.balance.domain;

import java.util.Optional;

public interface BalanceRepository {
    Balance save(Balance balance);
    Optional<Balance> findByUserId(Long userId);
}
