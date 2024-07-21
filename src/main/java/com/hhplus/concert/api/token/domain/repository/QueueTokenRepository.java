package com.hhplus.concert.api.token.domain.repository;

import com.hhplus.concert.api.token.domain.entity.QueueToken;
import com.hhplus.concert.api.token.domain.type.TokenStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueTokenRepository extends JpaRepository<QueueToken, Long> {

    Optional<QueueToken> findByUserId(Long userId);
    Optional<QueueToken> findByUuidAndTokenStatus(String uuid, TokenStatus tokenStatus);
    List<QueueToken> findAllByTokenStatusOrderByTokenIdDesc(TokenStatus tokenStatus);
    Long countAllByCreateDtBeforeAndTokenStatus(LocalDateTime createDt, TokenStatus tokenStatus);
    Optional<QueueToken> findByUuid(String uuid);
    List<QueueToken> findAllByExpirationTimeBeforeAndTokenStatus(LocalDateTime now, TokenStatus tokenStatus);
}
