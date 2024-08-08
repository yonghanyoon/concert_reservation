package com.hhplus.concert.api.token.application;

import com.hhplus.concert.api.token.domain.QueueToken;
import com.hhplus.concert.api.token.infrastructure.redis.repository.TokenRedisRepository;
import com.hhplus.concert.common.exception.list.CustomBadRequestException;
import com.hhplus.concert.common.exception.list.CustomForbiddenException;
import com.hhplus.concert.common.exception.list.CustomNotFoundException;
import java.sql.Timestamp;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final TokenRedisRepository tokenRedisRepository;
    @Value("${token.waiting.active}")
    private Long active;

    public QueueToken createWaitingToken(QueueToken queueToken) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (tokenRedisRepository.zRank(queueToken.getUserId()) == null) {
            log.warn(String.format("[대기열 토큰 발급] userId : %d -> 이미 토큰이 발급된 사용자", queueToken.getUserId()));
            throw new CustomBadRequestException(HttpStatus.BAD_REQUEST, "이미 토큰이 발급된 사용자");
        }
        tokenRedisRepository.zAdd(queueToken.getUserId(), (double) timestamp.getTime());
        Long userId = queueToken.getUserId();
        Long position = tokenRedisRepository.zRank(userId) + 1;
        Long waitingSeconds = position / active * 10;
        Long hours = waitingSeconds / 3600;
        Long minutes = (waitingSeconds % 3600) / 60;
        Long seconds = waitingSeconds % 60;
        String waitingTime = String.format("대기 예상 시간: %02d 시, %02d 분, %02d 초", hours, minutes, seconds);

        return new QueueToken(userId, position, waitingTime);

    }

    public QueueToken getWaitingToken(Long userId) {
        Long position = tokenRedisRepository.zRank(userId);
        if (position == null) {
            log.warn(String.format("[대기열 토큰 조회] userId : %d -> 존재하지 않는 토큰", userId));
            throw new CustomNotFoundException(HttpStatus.NOT_FOUND, "존재하지 않는 토큰");
        }
        position += 1;
        Long waitingSeconds = position / active * 10;
        Long hours = waitingSeconds / 3600;
        Long minutes = (waitingSeconds % 3600) / 60;
        Long seconds = waitingSeconds % 60;
        String waitingTime = String.format("대기 예상 시간: %02d 시, %02d 분, %02d 초", hours, minutes, seconds);

        return new QueueToken(userId, position, waitingTime);
    }

    public void tokenActiveCheck(Long userId) {
        if (!tokenRedisRepository.isMemberOfSet(userId)) {
            throw new CustomForbiddenException(HttpStatus.FORBIDDEN, "접근 권한 없음");
        }
    }

    public void tokenExpired(Long userId) {
        tokenRedisRepository.removeMember(String.valueOf(userId));
        log.info(String.format("[토큰 만료] userId : %d -> 결제 성공", userId));
    }

    public void tokenActive() {
        Set<Object> activeTokens = tokenRedisRepository.zRange();
        if (activeTokens.size() != 0) {
            tokenRedisRepository.zRem(activeTokens);
            log.info("[Waiting Tokens] : 제거");
            tokenRedisRepository.addToSet(activeTokens);
            log.info("[Active Tokens] : 입장");
        }
    }

}
