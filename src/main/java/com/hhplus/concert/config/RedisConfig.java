package com.hhplus.concert.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericToStringSerializer<>(Object.class));

        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        //

        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                                                                       .entryTtl(Duration.ofSeconds(60))
                                                                       .disableCachingNullValues()
                                                                       .serializeKeysWith(
                                                                           RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                                                                       )
                                                                       .serializeValuesWith(
                                                                           RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer)
                                                                       );

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        for (CacheType value : CacheType.values()) {
            cacheConfigurations.put(value.name(),
                                    RedisCacheConfiguration.defaultCacheConfig()
                                                           .prefixCacheNameWith("cache")
                                                           .entryTtl(Duration.ofSeconds(60))
                                                           .disableCachingNullValues()
                                                           .serializeKeysWith(
                                                               RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                                                           )
                                                           .serializeValuesWith(
                                                               RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer)
                                                           ));
        }


        return RedisCacheManager.builder(redisConnectionFactory)
                                .cacheDefaults(cacheConfig)
                                .withInitialCacheConfigurations(cacheConfigurations)
                                .build();
    }
}
