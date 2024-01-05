package com.zerobase.zbcharger.aop.lock;

import com.zerobase.zbcharger.util.CustomSpELParser;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * 분산 락을 사용하기 위한 AOP<br/> Redisson 을 사용하여 분산 락을 사용한다
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAop {

    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;

    @Around("@annotation(com.zerobase.zbcharger.aop.lock.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
        String key = getLockKey(signature.getParameterNames(), joinPoint.getArgs(),
            distributedLock.key());

        Object kvKey = kv("key", key);

        RLock rLock = redissonClient.getLock(key);

        try {
            boolean available = rLock.tryLock(distributedLock.waitTime(),
                distributedLock.leaseTime(), distributedLock.timeUnit());

            if (!available) {
                log.trace("redisson lock tryLock fail {}", kvKey);
                return false;
            }

            log.trace("redisson lock tryLock success {}", kvKey);
            return joinPoint.proceed();
        } catch (InterruptedException e) {
            throw new InterruptedException();
        } finally {
            try {
                rLock.unlock();
                log.trace("redisson lock unlock success {}", kvKey);
            } catch (IllegalMonitorStateException e) {
                log.error("redisson lock already unlock {} {}",
                    kv("method", method.getName()),
                    kv("key", key)
                );
            }
        }
    }

    private static String getLockKey(String[] parameterNames, Object[] args, String key) {
        return REDISSON_LOCK_PREFIX + CustomSpELParser.getDynamicValue(parameterNames, args, key);
    }

    private static Object kv(String method, String name) {
        return String.format("%s=%s", method, name);
    }
}
