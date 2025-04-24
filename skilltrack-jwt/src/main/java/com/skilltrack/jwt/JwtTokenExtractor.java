package com.skilltrack.jwt;

import com.skilltrack.jwt.model.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.skilltrack.jwt.constant.JwtConstants.AUTHORITIES_CLAIM;
import static com.skilltrack.jwt.constant.JwtConstants.TOKEN_PREFIX;
import static com.skilltrack.jwt.constant.JwtConstants.USER_ID_CLAIM;

@Service
@RequiredArgsConstructor
public class JwtTokenExtractor {
    private final SecretKeyProvider secretKeyProvider;

    public JwtUserDetails extractUserDetails(String token) {
        token = removeTokenPrefix(token);
        Claims claims = getClaims(token);

        JwtUserDetails userDetails = new JwtUserDetails();
        userDetails.setUserId(UUID.fromString(claims.get(USER_ID_CLAIM, String.class)));
        userDetails.setUsername(claims.getSubject());

        @SuppressWarnings("unchecked")
        List<String> authorities = claims.get(AUTHORITIES_CLAIM, List.class);
        userDetails.setAuthorities(authorities);

        return userDetails;
    }

    public UUID extractUserId(String token) {
        token = removeTokenPrefix(token);
        Claims claims = getClaims(token);
        return UUID.fromString(claims.get(USER_ID_CLAIM, String.class));
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKeyProvider.getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String removeTokenPrefix(String token) {
        if (token.startsWith(TOKEN_PREFIX)) {
            return token.substring(7);
        }
        return token;
    }
}
