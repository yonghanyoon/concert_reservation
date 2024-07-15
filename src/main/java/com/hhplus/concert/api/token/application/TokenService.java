package com.hhplus.concert.api.token.application;

import com.hhplus.concert.api.token.domain.repository.QueueTokenRepository;
import com.hhplus.concert.api.token.domain.entity.QueueToken;
import com.hhplus.concert.api.token.domain.type.TokenStatus;
import com.hhplus.concert.exception.list.CustomBadRequestException;
import com.hhplus.concert.exception.list.CustomForbiddenException;
import com.hhplus.concert.exception.list.CustomNotFoundException;
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
    private final QueueTokenRepository jpaAuthRepository;

    @Transactional
    public QueueToken saveToken(QueueToken queueToken) {
        Optional<QueueToken> item = jpaAuthRepository.findByUserId(queueToken.getUserId());
        if (item.isPresent()) {
            if (!item.get().getTokenStatus().equals(TokenStatus.EXPIRED)) {
                throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "이미 토큰이 발급된 사용자");
            }
            queueToken.setTokenId(item.get().getTokenId());
        }
        queueToken.setUuid(UUID.randomUUID().toString());
        queueToken.setTokenStatus(TokenStatus.RESERVED);
        queueToken.setCreateDt(LocalDateTime.now());
        return jpaAuthRepository.save(queueToken);
    }

    @Transactional
    public QueueToken getToken(String uuid) {
            QueueToken queueToken = jpaAuthRepository.findByUuidAndTokenStatus(uuid, TokenStatus.RESERVED).orElseThrow(() -> {
                throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "해당 UUID는 존재하지 않습니다.");
            });
        List<QueueToken> availableQueueTokens = jpaAuthRepository.findAllByTokenStatusOrderByTokenIdDesc(TokenStatus.AVAILABLE);
        if (availableQueueTokens.size() >= 10 || queueToken.getPosition() == null) {
            Long position = jpaAuthRepository.countAllByCreateDtBeforeAndTokenStatus(queueToken.getCreateDt(), TokenStatus.RESERVED) + 1;
            queueToken.setPosition(position);
            return jpaAuthRepository.save(queueToken);
        } else {
            if (queueToken.getPosition() == 1) {
                queueToken.setTokenStatus(TokenStatus.AVAILABLE);
                queueToken.setPosition(null);
                queueToken.setExpirationTime(LocalDateTime.now().plusMinutes(3));
                jpaAuthRepository.save(queueToken);
            }
            return queueToken;
        }
    }

    @Transactional
    public void tokenStatusCheck(String uuid) {
        QueueToken queueToken = jpaAuthRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "해당 UUID는 존재하지 않습니다.");
        });
        if (!TokenStatus.AVAILABLE.equals(queueToken.getTokenStatus())) {
            throw new CustomForbiddenException(HttpStatus.FORBIDDEN, "접근 권한 없음");
        } else {
            queueToken.setExpirationTime(LocalDateTime.now().plusMinutes(10));
            jpaAuthRepository.save(queueToken);
        }
    }

    public void tokenExpired(String uuid) {
        QueueToken queueToken = jpaAuthRepository.findByUuid(uuid).get();
        queueToken.setTokenStatus(TokenStatus.EXPIRED);
        jpaAuthRepository.save(queueToken);
    }

    public void tokenExpiredCheck() {
        List<QueueToken> expiredQueueToken = jpaAuthRepository.findAllByExpirationTimeBeforeAndTokenStatus(LocalDateTime.now(), TokenStatus.AVAILABLE);
        if (expiredQueueToken.size() == 0) return;
        for (QueueToken queueToken : expiredQueueToken) {
            queueToken.setTokenStatus(TokenStatus.EXPIRED);
        }
        jpaAuthRepository.saveAll(expiredQueueToken);
    }

}
