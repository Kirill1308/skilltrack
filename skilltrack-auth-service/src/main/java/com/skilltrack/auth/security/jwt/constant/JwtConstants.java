package com.skilltrack.auth.security.jwt.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String USER_ID_CLAIM = "id";
    public static final String AUTHORITIES_CLAIM = "roles";
}
