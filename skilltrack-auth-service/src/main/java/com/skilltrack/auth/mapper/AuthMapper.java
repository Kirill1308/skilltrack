package com.skilltrack.auth.mapper;

import com.skilltrack.auth.dto.request.RegistrationRequest;
import com.skilltrack.auth.dto.response.AuthenticationResponse;
import com.skilltrack.auth.dto.response.RegistrationResponse;
import com.skilltrack.auth.model.UserAuth;
import com.skilltrack.common.constant.Role;
import com.skilltrack.common.dto.user.response.UserProfileResponse;
import com.skilltrack.common.util.PasswordEncoderUtil;
import com.skilltrack.jwt.model.TokenPair;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthMapper {

    public UserAuth toUserAuth(RegistrationRequest request, UserProfileResponse profile) {
        return UserAuth.builder()
                .userId(profile.getId())
                .email(request.getEmail())
                .password(PasswordEncoderUtil.encode(request.getPassword()))
                .roles(Set.of(Role.ROLE_USER))
                .active(true)
                .emailVerified(false)
                .build();
    }

    public RegistrationResponse toRegistrationResponse(UserAuth user, UserProfileResponse profile) {
        return RegistrationResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .roles(user.getRoles())
                .department(profile.getDepartment())
                .emailVerified(user.isEmailVerified())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public AuthenticationResponse toAuthResponse(UserAuth user, UserProfileResponse profile, TokenPair tokens) {
        return AuthenticationResponse.builder()
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .userId(user.getUserId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }
}
