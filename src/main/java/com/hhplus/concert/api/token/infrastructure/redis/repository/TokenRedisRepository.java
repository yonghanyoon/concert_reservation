package com.hhplus.concert.api.token.infrastructure.redis.repository;


import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, String> setRedisTemplate;
    @Value("${token.waiting.waiting-key}")
    private String waitingKey;
    @Value("${token.waiting.active}")
    private long active;
    @Value("${token.active-key}")
    private String activeKey;

    public void zAdd(Long value, double score) {
        redisTemplate.opsForZSet().add(waitingKey, value, score);
    }

    public Long zRank(Long value) {
        return redisTemplate.opsForZSet().rank(waitingKey, value);
    }

    public void zRem(Set<Object> values) {
        String[] valueArray = values.stream().map(Object::toString).toArray(String[]::new);
        redisTemplate.opsForZSet().remove(waitingKey, valueArray);
    }

    public Set<Object> zRange() {
        return redisTemplate.opsForZSet().range(waitingKey, 0, active - 1);
    }

    public void addToSet(Set<Object> values) {
        String[] valueArray = values.stream().map(Object::toString).toArray(String[]::new);
        setRedisTemplate.opsForSet().add(activeKey, valueArray);
    }

    public boolean isMemberOfSet(Long value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(activeKey, value));
    }

    public void removeMember(String value) {
        setRedisTemplate.opsForSet().remove(activeKey, value);
    }
}
