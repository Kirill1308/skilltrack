package com.skilltrack.auth.service.impl;

import com.skilltrack.auth.dto.request.ForgotPasswordRequest;
import com.skilltrack.auth.dto.request.LoginRequest;
import com.skilltrack.auth.dto.request.RegistrationRequest;
import com.skilltrack.auth.dto.request.ResetPasswordRequest;
import com.skilltrack.auth.dto.request.TokenRefreshRequest;
import com.skilltrack.auth.dto.response.AuthenticationResponse;
import com.skilltrack.auth.dto.response.RegistrationResponse;
import com.skilltrack.auth.dto.response.TokenRefreshResponse;
import com.skilltrack.auth.exception.InvalidCredentialsException;
import com.skilltrack.auth.exception.PasswordMismatchException;
import com.skilltrack.auth.exception.UserInactiveException;
import com.skilltrack.auth.exception.UserNotFoundException;
import com.skilltrack.auth.mapper.AuthMapper;
import com.skilltrack.auth.mapper.UserProfileMapper;
import com.skilltrack.auth.model.UserAuth;
import com.skilltrack.auth.repository.UserAuthRepository;
import com.skilltrack.auth.security.jwt.model.JwtTokens;
import com.skilltrack.auth.security.jwt.service.JwtService;
import com.skilltrack.auth.service.AuthService;
import com.skilltrack.auth.service.TokenService;
import com.skilltrack.common.client.UserServiceClient;
import com.skilltrack.common.constant.TokenType;
import com.skilltrack.common.dto.user.request.UserProfileCreateRequest;
import com.skilltrack.common.dto.user.response.UserProfileResponse;
import com.skilltrack.common.messaging.NotificationEventProducer;
import com.skilltrack.common.util.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserAuthRepository authRepository;

    private final UserServiceClient userServiceClient;
    private final NotificationEventProducer notificationProducer;

    private final JwtService jwtService;
    private final TokenService tokenService;

    private final AuthMapper authMapper;
    private final UserProfileMapper profileMapper;

    @Override
    @Transactional
    public RegistrationResponse registerUser(RegistrationRequest request) {
        UserProfileCreateRequest profileCreateRequest = profileMapper.toCreateRequest(request);

        UserProfileResponse profile = userServiceClient.createUserProfile(profileCreateRequest);

        UserAuth user = authMapper.toUserAuth(request, profile);

        UserAuth savedUser = authRepository.save(user);

        String verificationToken = tokenService.createToken(user, TokenType.EMAIL_VERIFICATION, 7);
        notificationProducer.sendVerificationEmail(savedUser.getUserId(), savedUser.getEmail(), verificationToken);

        return authMapper.toRegistrationResponse(savedUser, profile);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse loginUser(LoginRequest request) {
        UserAuth user = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        if (!PasswordEncoderUtil.matches(request.getPassword(), user.getPassword()))
            throw new InvalidCredentialsException("Invalid password");

        if (!user.isActive()) throw new UserInactiveException();

        UserProfileResponse profile = userServiceClient.getUserProfileById(user.getUserId());

        Set<String> roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        JwtTokens tokens = jwtService.generateTokens(user.getUserId(), user.getEmail(), roles);

        return authMapper.toAuthResponse(user, profile, tokens);
    }

    @Override
    @Transactional(readOnly = true)
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        UUID userId = jwtService.extractUserId(request.getRefreshToken());

        UserAuth user = authRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!user.isActive()) throw new UserInactiveException();

        Set<String> roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        JwtTokens newTokens = jwtService.generateTokens(user.getUserId(), user.getEmail(), roles);

        return TokenRefreshResponse.builder()
                .accessToken(newTokens.getAccessToken())
                .refreshToken(newTokens.getRefreshToken())
                .expiresIn(jwtService.getAccessTokenExpirationInSeconds())
                .build();
    }

    @Override
    @Transactional
    public void initiatePasswordReset(ForgotPasswordRequest request) {
        UserAuth user = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        if (!user.isActive()) throw new UserInactiveException();

        tokenService.deleteUserTokensByType(user.getUserId(), TokenType.PASSWORD_RESET);

        String resetToken = tokenService.createToken(user, TokenType.PASSWORD_RESET, 1);

        notificationProducer.sendPasswordResetEmail(user.getUserId(), user.getEmail(), resetToken);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }

        UserAuth user = tokenService.validateAndConsumeToken(request.getToken(), TokenType.PASSWORD_RESET);

        user.setPassword(PasswordEncoderUtil.encode(request.getNewPassword()));
        authRepository.save(user);
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {
        UserAuth user = tokenService.validateAndConsumeToken(token, TokenType.EMAIL_VERIFICATION);
        user.setEmailVerified(true);
        authRepository.save(user);
    }

}
