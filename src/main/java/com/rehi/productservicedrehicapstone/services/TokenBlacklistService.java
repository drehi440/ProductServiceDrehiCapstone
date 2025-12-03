package com.rehi.productservicedrehicapstone.services;

public interface TokenBlacklistService {

    void blacklistToken(String token, long millisToExpire);

    boolean isBlacklisted(String token);
}


