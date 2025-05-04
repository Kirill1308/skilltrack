package com.skilltrack.auth.service;

import com.skilltrack.auth.model.UserAuth;
import com.skilltrack.auth.model.TokenType;

import java.util.UUID;

public interface TokenService {
    String createToken(UserAuth user, TokenType tokenType, int expiryDays);

    UserAuth validateAndConsumeToken(String tokenValue, TokenType tokenType);

    void deleteUserTokensByType(UUID userId, TokenType tokenType);

    void cleanupExpiredTokens();
}
