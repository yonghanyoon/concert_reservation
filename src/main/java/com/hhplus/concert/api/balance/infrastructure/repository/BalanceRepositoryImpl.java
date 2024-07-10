package com.hhplus.concert.api.balance.infrastructure.repository;

import com.hhplus.concert.api.balance.domain.Balance;
import com.hhplus.concert.api.balance.domain.BalanceRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BalanceRepositoryImpl implements BalanceRepository {

    private final JpaBalanceRepository jpaBalanceRepository;

    @Autowired
    public BalanceRepositoryImpl(JpaBalanceRepository jpaBalanceRepository) {
        this.jpaBalanceRepository = jpaBalanceRepository;
    }

    @Override
    public Balance save(Balance balance) {
        return jpaBalanceRepository.save(balance);
    }

    @Override
    public Optional<Balance> findByUserId(Long userId) {
        return jpaBalanceRepository.findByUserId(userId);
    }
}
