package com.hhplus.concert.api.token.application;

import com.hhplus.concert.api.token.domain.repository.QueueTokenRepository;
import com.hhplus.concert.api.token.domain.entity.QueueToken;
import com.hhplus.concert.api.token.domain.type.TokenStatus;
import com.hhplus.concert.common.exception.list.CustomBadRequestException;
import com.hhplus.concert.common.exception.list.CustomForbiddenException;
import com.hhplus.concert.common.exception.list.CustomNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final QueueTokenRepository queueTokenRepository;

    @Transactional
    public QueueToken saveToken(QueueToken queueToken) {
        Optional<QueueToken> item = queueTokenRepository.findByUserId(queueToken.getUserId());
        if (item.isPresent()) {
            if (!item.get().getTokenStatus().equals(TokenStatus.EXPIRED)) {
                throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "이미 토큰이 발급된 사용자");
            }
            queueToken.createToken(item.get().getTokenId());
        }
        queueToken.reservedToken(UUID.randomUUID().toString(), TokenStatus.RESERVED, LocalDateTime.now());
        return queueTokenRepository.save(queueToken);
    }

    @Transactional
    public QueueToken getToken(String uuid) {
            QueueToken queueToken = queueTokenRepository.findByUuidAndTokenStatus(uuid, TokenStatus.RESERVED).orElseThrow(() -> {
                throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "해당 UUID는 존재하지 않습니다.");
            });
        List<QueueToken> availableQueueTokens = queueTokenRepository.findAllByTokenStatusOrderByTokenIdDesc(TokenStatus.AVAILABLE);
        Long position = queueTokenRepository.countAllByCreateDtBeforeAndTokenStatus(queueToken.getCreateDt(), TokenStatus.RESERVED) + 1;
        if (availableQueueTokens.size() >= 10 || queueToken.getPosition() == null) {
            queueToken.updatePosition(position);
            return queueTokenRepository.save(queueToken);
        }

        queueToken.updatePosition(position);
        if (queueToken.getPosition() == 1) {
            queueToken.updateAvailable(TokenStatus.AVAILABLE, null, LocalDateTime.now().plusMinutes(3));
        }
        return queueToken;
    }

    // 토큰 검증
    @Transactional
    public void tokenStatusCheck(String uuid) {
        QueueToken queueToken = queueTokenRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "해당 UUID는 존재하지 않습니다.");
        });
        if (!TokenStatus.AVAILABLE.equals(queueToken.getTokenStatus())) {
            throw new CustomForbiddenException(HttpStatus.FORBIDDEN, "접근 권한 없음");
        } else {
            queueToken.updateExpirationTime(LocalDateTime.now().plusMinutes(10));
        }
    }

    // 결제 완료 토큰 만료
    public void tokenExpired(String uuid) {
        QueueToken queueToken = queueTokenRepository.findByUuid(uuid).get();
        queueToken.updateExpired(TokenStatus.EXPIRED);
    }

    // 토큰 만료시간 체크
    @Transactional
    public void tokenExpiredCheck() {
        List<QueueToken> expiredQueueToken = queueTokenRepository.findAllByExpirationTimeBeforeAndTokenStatus(LocalDateTime.now(), TokenStatus.AVAILABLE);
        if (expiredQueueToken.size() == 0) return;
        for (QueueToken queueToken : expiredQueueToken) {
            queueToken.updateExpired(TokenStatus.EXPIRED);
        }
    }

}
