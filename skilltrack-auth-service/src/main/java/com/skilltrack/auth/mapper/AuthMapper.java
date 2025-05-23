package com.skilltrack.auth.mapper;

import com.skilltrack.auth.dto.request.RegistrationRequest;
import com.skilltrack.auth.dto.response.AuthenticationResponse;
import com.skilltrack.auth.dto.response.RegistrationResponse;
import com.skilltrack.auth.model.UserAuth;
import com.skilltrack.auth.security.jwt.model.JwtTokens;
import com.skilltrack.auth.model.Role;
import com.skilltrack.common.dto.user.response.UserProfileResponse;
import com.skilltrack.auth.util.PasswordEncoderUtil;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthMapper {

    public UserAuth toUserAuth(RegistrationRequest request, UserProfileResponse profile) {
        return UserAuth.builder()
                .userId(profile.getId())
                .email(request.getEmail())
                .password(PasswordEncoderUtil.encode(request.getPassword()))
                .roles(Set.of(Role.ROLE_USER))
                .emailVerified(false)
                .build();
    }

    public RegistrationResponse toRegistrationResponse(UserAuth user, UserProfileResponse profile) {
        return RegistrationResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .roles(user.getRoles().stream().map(Role::name).collect(Collectors.toSet()))
                .department(profile.getDepartment())
                .emailVerified(user.isEmailVerified())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public AuthenticationResponse toAuthResponse(UserAuth user, UserProfileResponse profile, JwtTokens tokens) {
        return AuthenticationResponse.builder()
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .userId(user.getUserId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(Role::name).collect(Collectors.toSet()))
                .build();
    }
}
