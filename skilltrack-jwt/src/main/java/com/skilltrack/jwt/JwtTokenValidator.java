package com.skilltrack.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.skilltrack.jwt.constant.JwtConstants.TOKEN_PREFIX;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenValidator {
    private final SecretKeyProvider secretKeyProvider;

    public boolean isValid(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        token = removeTokenPrefix(token);

        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretKeyProvider.getKey())
                    .build()
                    .parseSignedClaims(token);
            return claims.getPayload()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            log.error("Token validation failed", e);
            return false;
        }
    }

    private String removeTokenPrefix(String token) {
        if (token.startsWith(TOKEN_PREFIX)) {
            return token.substring(7);
        }
        return token;
    }
}
