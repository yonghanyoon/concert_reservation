package com.hhplus.concert.api.auth.infrastructure.repository;

import com.hhplus.concert.api.auth.domain.QueueToken;
import com.hhplus.concert.api.auth.domain.type.TokenStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuthRepository extends JpaRepository<QueueToken, Long> {

    Optional<QueueToken> findByUserId(Long userId);
    Optional<QueueToken> findByUuidAndTokenStatus(String uuid, TokenStatus tokenStatus);
    List<QueueToken> findAllByTokenStatusOrderByTokenIdDesc(TokenStatus tokenStatus);
}
