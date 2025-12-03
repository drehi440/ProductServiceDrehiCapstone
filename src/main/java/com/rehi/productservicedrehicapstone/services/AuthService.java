package com.rehi.productservicedrehicapstone.services;

public interface AuthService {

    /**
     * Log out the current session by blacklisting the JWT until its expiry time.
     */
    void logout(String token);
}


