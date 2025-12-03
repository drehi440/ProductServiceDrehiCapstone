package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    public void logout(String token) {
        Date expiration = jwtUtil.extractExpiration(token);
        long remainingMillis = expiration.getTime() - System.currentTimeMillis();
        if (remainingMillis <= 0) {
            return;
        }

        // In a production setup, Redis-backed blacklist ensures server-side token invalidation.
        // If Redis is not available, the fallback is that the client must discard the token.
        tokenBlacklistService.blacklistToken(token, remainingMillis);
        log.info("JWT token blacklisted for remainingMillis={}", remainingMillis);
    }
}


