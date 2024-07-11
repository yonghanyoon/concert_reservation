package com.hhplus.concert.api.auth.domain;

import com.hhplus.concert.api.auth.domain.type.TokenStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuthRepository {
    QueueToken save(QueueToken queueToken);
    Optional<QueueToken> findByUserId(Long userId);
    Optional<QueueToken> findByUuidAndTokenStatus(String uuid, TokenStatus tokenStatus);
    List<QueueToken> findAllByTokenStatusOrderByTokenIdDesc(TokenStatus tokenStatus);
    Long countAllByCreateDtBeforeAndTokenStatus(LocalDateTime createDt, TokenStatus tokenStatus);
}
