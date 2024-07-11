package com.hhplus.concert.api.auth.application;

import com.hhplus.concert.api.auth.domain.AuthRepository;
import com.hhplus.concert.api.auth.domain.QueueToken;
import com.hhplus.concert.api.auth.domain.type.TokenStatus;
import com.hhplus.concert.exception.list.CustomBadRequestException;
import com.hhplus.concert.exception.list.CustomNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Transactional
    public QueueToken saveToken(QueueToken queueToken) {
        Optional<QueueToken> item = authRepository.findByUserId(queueToken.getUserId());
        if (item.isPresent()) {
            if (!item.get().getTokenStatus().equals(TokenStatus.EXPIRED)) {
                throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "이미 토큰이 발급된 사용자");
            }
            queueToken.setTokenId(item.get().getTokenId());
        }
        queueToken.setTokenStatus(TokenStatus.RESERVED);
            queueToken.setCreateDt(LocalDateTime.now());
        return authRepository.save(queueToken);
    }

    @Transactional
    public QueueToken getToken(String uuid) {
            QueueToken queueToken = authRepository.findByUuidAndTokenStatus(uuid, TokenStatus.RESERVED).orElseThrow(() -> {
                throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "해당 UUID는 존재하지 않습니다.");
            });
        List<QueueToken> availableQueueTokens = authRepository.findAllByTokenStatusOrderByTokenIdDesc(TokenStatus.AVAILABLE);
        if (availableQueueTokens.size() >= 10) {
            Long position = authRepository.countAllByCreateDtBeforeAndTokenStatus(queueToken.getCreateDt(), TokenStatus.RESERVED) + 1;
            queueToken.setPosition(position);
            return authRepository.save(queueToken);
        } else {
            if (queueToken.getPosition() == 1) {
                queueToken.setTokenStatus(TokenStatus.AVAILABLE);
                queueToken.setPosition(null);
                queueToken.setExpirationTime(LocalDateTime.now().plusMinutes(30));
                authRepository.save(queueToken);
            }
            return queueToken;
        }
    }


}
