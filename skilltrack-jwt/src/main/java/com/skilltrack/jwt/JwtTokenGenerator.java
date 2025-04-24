package com.skilltrack.jwt;

import com.skilltrack.jwt.model.TokenPair;
import com.skilltrack.jwt.props.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final JwtProperties jwtProperties;
    private final SecretKeyProvider secretKeyProvider;

    public TokenPair generateTokens(UUID id, String email, Set<String> roles) {
        String accessToken = generateAccessToken(id, email, roles);
        String refreshToken = generateRefreshToken(id);
        return new TokenPair(accessToken, refreshToken);
    }

    public String generateAccessToken(UUID id, String email, Set<String> roles) {
        Claims claims = Jwts.claims()
                .subject(email)
                .add("id", id)
                .add("roles", roles)
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getExpiration(), ChronoUnit.HOURS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(secretKeyProvider.getKey())
                .compact();
    }


    public String generateRefreshToken(UUID id) {
        Claims claims = Jwts.claims()
                .add("id", id)
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getRefreshExpiration(), ChronoUnit.HOURS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(secretKeyProvider.getKey())
                .compact();
    }

}
