package com.rehi.productservicedrehicapstone.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisTokenBlacklistService implements TokenBlacklistService {

    private static final Logger log = LoggerFactory.getLogger(RedisTokenBlacklistService.class);
    private static final String PREFIX = "jwt:blacklist:";

    private final StringRedisTemplate redisTemplate;

    public RedisTokenBlacklistService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void blacklistToken(String token, long millisToExpire) {
        try {
            long seconds = Math.max(1, millisToExpire / 1000);
            redisTemplate.opsForValue().set(PREFIX + token, "1", Duration.ofSeconds(seconds));
        } catch (DataAccessException ex) {
            // In a production setup, Redis should be available for blacklisting tokens.
            // If Redis is not available, the client must discard the token on logout.
            log.warn("Redis not available for token blacklist; token will not be invalidated server-side.", ex);
        }
    }

    @Override
    public boolean isBlacklisted(String token) {
        try {
            Boolean exists = redisTemplate.hasKey(PREFIX + token);
            return Boolean.TRUE.equals(exists);
        } catch (DataAccessException ex) {
            // If Redis is unavailable, treat token as not blacklisted.
            log.warn("Redis not reachable while checking token blacklist; allowing token.", ex);
            return false;
        }
    }
}


