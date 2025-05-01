package com.skilltrack.auth.security.jwt.utils;

import com.skilltrack.auth.security.jwt.SecretKeyProvider;
import com.skilltrack.auth.security.jwt.model.JwtTokens;
import com.skilltrack.auth.security.jwt.props.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static com.skilltrack.auth.security.jwt.constant.JwtConstants.AUTHORITIES_CLAIM;
import static com.skilltrack.auth.security.jwt.constant.JwtConstants.USER_ID_CLAIM;


@Service
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final JwtProperties jwtProperties;
    private final SecretKeyProvider secretKeyProvider;

    public JwtTokens generateTokens(UUID id, String email, Set<String> roles) {
        String accessToken = generateAccessToken(id, email, roles);
        String refreshToken = generateRefreshToken(id);
        return new JwtTokens(accessToken, refreshToken);
    }

    public String generateAccessToken(UUID id, String email, Set<String> roles) {
        Claims claims = Jwts.claims()
                .subject(email)
                .add(USER_ID_CLAIM, id)
                .add(AUTHORITIES_CLAIM, roles)
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
                .add(USER_ID_CLAIM, id)
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
