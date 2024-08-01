package com.hhplus.concert.config;

import java.util.concurrent.TimeUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Configuration
public class RedisLockAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;
    private final ExpressionParser parser = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Around("@annotation(redisLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String lockKey = parseKey(redisLock.key(), methodSignature, joinPoint.getArgs());
        long timeout = redisLock.timeout();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        boolean isLocked = valueOperations.setIfAbsent(lockKey, "LOCKED", timeout, TimeUnit.MILLISECONDS);
        if (!isLocked) {
            throw new RuntimeException("락 획득 실패 : " + lockKey);
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("transactionWithLock");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            Object result = joinPoint.proceed();
            transactionManager.commit(status);
            return result;
        } catch (Throwable ex) {
            transactionManager.rollback(status);
            throw ex;
        } finally {
            redisTemplate.delete(lockKey);
        }
    }

    private String parseKey(String keyExpression, MethodSignature methodSignature, Object[] args) {
        EvaluationContext context = new StandardEvaluationContext();
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return parser.parseExpression(keyExpression).getValue(context, String.class);
    }
}
