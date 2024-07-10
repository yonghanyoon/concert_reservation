package com.hhplus.concert.api.auth.infrastructure.repository;

import com.hhplus.concert.api.auth.domain.AuthRepository;
import com.hhplus.concert.api.auth.domain.QueueToken;
import com.hhplus.concert.api.auth.domain.type.TokenStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepositoryImpl implements AuthRepository {

    private final JpaAuthRepository jpaAuthRepository;

    @Autowired
    public AuthRepositoryImpl(JpaAuthRepository jpaAuthRepository) {
        this.jpaAuthRepository = jpaAuthRepository;
    }

    @Override
    public QueueToken save(QueueToken queueToken) {
        return jpaAuthRepository.save(queueToken);
    }

    @Override
    public Optional<QueueToken> findByUserId(Long userId) {
        return jpaAuthRepository.findByUserId(userId);
    }

    @Override
    public Optional<QueueToken> findByUuidAndTokenStatus(String uuid, TokenStatus tokenStatus) {
        return jpaAuthRepository.findByUuidAndTokenStatus(uuid, tokenStatus);
    }

    @Override
    public List<QueueToken> findAllByTokenStatusOrderByTokenIdDesc(TokenStatus tokenStatus) {
        return jpaAuthRepository.findAllByTokenStatusOrderByTokenIdDesc(tokenStatus);
    }
}
