package com.skilltrack.auth.service.impl;

import com.skilltrack.auth.exception.InvalidTokenException;
import com.skilltrack.auth.exception.TokenExpiredException;
import com.skilltrack.auth.model.Token;
import com.skilltrack.auth.model.TokenType;
import com.skilltrack.auth.model.UserAuth;
import com.skilltrack.auth.repository.TokenRepository;
import com.skilltrack.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public String createToken(UserAuth user, TokenType tokenType, int expiryDays) {
        tokenRepository.deleteByUserIdAndTokenType(user.getId(), tokenType);

        String tokenValue = String.format("%06d", new Random().nextInt(1000000));
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(expiryDays);

        Token token = Token.builder()
                .token(tokenValue)
                .user(user)
                .tokenType(tokenType)
                .expiryDate(expiryDate)
                .build();

        tokenRepository.save(token);
        return tokenValue;
    }

    public UserAuth validateToken(String tokenValue, TokenType tokenType) {
        Token token = tokenRepository.findByTokenAndTokenType(tokenValue, tokenType)
                .orElseThrow(() -> new InvalidTokenException("Invalid token: " + tokenValue));

        if (token.isExpired()) {
            tokenRepository.delete(token);

            throw new TokenExpiredException("Token has expired: " + tokenValue);
        }

        return token.getUser();
    }

    public UserAuth validateAndConsumeToken(String tokenValue, TokenType tokenType) {
        UserAuth user = validateToken(tokenValue, tokenType);
        tokenRepository.deleteByTokenAndTokenType(tokenValue, tokenType);
        return user;
    }

    public void deleteUserTokensByType(UUID userId, TokenType tokenType) {
        tokenRepository.deleteByUserIdAndTokenType(userId, tokenType);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    //    TODO: Configure scheduler
    public void cleanupExpiredTokens() {
        tokenRepository.deleteAllExpiredTokens(LocalDateTime.now());
    }

}
