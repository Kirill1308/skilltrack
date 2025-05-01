package com.skilltrack.auth.security.jwt;

import com.skilltrack.auth.security.jwt.props.JwtProperties;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor
public class SecretKeyProvider {

    @Getter
    private SecretKey key;
    private final JwtProperties jwtProperties;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

}
