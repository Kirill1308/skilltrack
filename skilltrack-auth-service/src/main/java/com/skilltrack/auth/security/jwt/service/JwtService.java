package com.skilltrack.auth.security.jwt.service;

import com.skilltrack.auth.security.jwt.utils.JwtTokenExtractor;
import com.skilltrack.auth.security.jwt.utils.JwtTokenGenerator;
import com.skilltrack.auth.security.jwt.model.JwtTokens;
import com.skilltrack.auth.security.jwt.props.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final JwtTokenExtractor jwtTokenExtractor;

    public JwtTokens generateTokens(UUID userId, String email, Set<String> roles) {
        return jwtTokenGenerator.generateTokens(userId, email, roles);
    }

    public UUID extractUserId(String token) {
        return jwtTokenExtractor.extractUserId(token);
    }

    public long getAccessTokenExpirationInSeconds() {
        return jwtProperties.getExpiration() * 3600;
    }
}
