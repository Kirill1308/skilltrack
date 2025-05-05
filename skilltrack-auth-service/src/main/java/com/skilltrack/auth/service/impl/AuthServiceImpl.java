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
import com.skilltrack.auth.exception.UserNotFoundException;
import com.skilltrack.auth.mapper.AuthMapper;
import com.skilltrack.auth.mapper.UserProfileMapper;
import com.skilltrack.auth.messaging.NotificationEventProducer;
import com.skilltrack.auth.model.TokenType;
import com.skilltrack.auth.model.UserAuth;
import com.skilltrack.auth.repository.UserAuthRepository;
import com.skilltrack.auth.security.jwt.model.JwtTokens;
import com.skilltrack.auth.security.jwt.service.JwtService;
import com.skilltrack.auth.service.AuthService;
import com.skilltrack.auth.service.TokenService;
import com.skilltrack.auth.util.PasswordEncoderUtil;
import com.skilltrack.common.client.UserServiceClient;
import com.skilltrack.common.dto.user.request.UserProfileCreateRequest;
import com.skilltrack.common.dto.user.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("Starting user registration process for email: {}", request.getEmail());

        UserProfileCreateRequest profileCreateRequest = profileMapper.toCreateRequest(request);
        UserProfileResponse profile = userServiceClient.createUserProfile(profileCreateRequest);
        log.info("User profile created successfully with ID: {}", profile.getId());

        UserAuth user = authMapper.toUserAuth(request, profile);
        UserAuth savedUser = authRepository.save(user);
        log.info("User auth record created successfully for user ID: {}", savedUser.getUserId());

        String verificationToken = tokenService.createToken(user, TokenType.EMAIL_VERIFICATION, 7);
        notificationProducer.sendVerificationEmail(savedUser.getUserId(), savedUser.getEmail(), verificationToken);
        log.info("Verification email sent to user ID: {}", savedUser.getUserId());

        RegistrationResponse response = authMapper.toRegistrationResponse(savedUser, profile);
        log.info("User registration completed successfully for email: {}", request.getEmail());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse loginUser(LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());

        UserAuth user = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed - User not found with email: {}", request.getEmail());
                    return new UserNotFoundException("User not found with email: " + request.getEmail());
                });

        if (!PasswordEncoderUtil.matches(request.getPassword(), user.getPassword())) {
            log.warn("Login failed - Invalid password for email: {}", request.getEmail());
            throw new InvalidCredentialsException("Invalid password");
        }

        UserProfileResponse profile = userServiceClient.getUserProfileById(user.getUserId());
        log.debug("User profile retrieved for user ID: {}", user.getUserId());

        Set<String> roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        JwtTokens tokens = jwtService.generateTokens(user.getUserId(), user.getEmail(), roles);
        log.info("JWT tokens generated successfully for user ID: {}", user.getUserId());

        AuthenticationResponse response = authMapper.toAuthResponse(user, profile, tokens);
        log.info("Login successful for email: {}", request.getEmail());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        log.info("Token refresh request received");

        UUID userId = jwtService.extractUserId(request.getRefreshToken());
        log.debug("Extracted user ID from refresh token: {}", userId);

        UserAuth user = authRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.warn("Token refresh failed - User not found with ID: {}", userId);
                    return new UserNotFoundException(userId);
                });

        Set<String> roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        JwtTokens newTokens = jwtService.generateTokens(user.getUserId(), user.getEmail(), roles);
        log.info("New JWT tokens generated successfully for user ID: {}", userId);

        TokenRefreshResponse response = TokenRefreshResponse.builder()
                .accessToken(newTokens.getAccessToken())
                .refreshToken(newTokens.getRefreshToken())
                .expiresIn(jwtService.getAccessTokenExpirationInSeconds())
                .build();

        log.info("Token refresh completed successfully for user ID: {}", userId);
        return response;
    }

    @Override
    @Transactional
    public void initiatePasswordReset(ForgotPasswordRequest request) {
        log.info("Password reset initiated for email: {}", request.getEmail());

        UserAuth user = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Password reset failed - User not found with email: {}", request.getEmail());
                    return new UserNotFoundException("User not found with email: " + request.getEmail());
                });

        tokenService.deleteUserTokensByType(user.getUserId(), TokenType.PASSWORD_RESET);
        log.debug("Previous password reset tokens deleted for user ID: {}", user.getUserId());

        String resetToken = tokenService.createToken(user, TokenType.PASSWORD_RESET, 1);
        log.debug("Password reset token generated for user ID: {}", user.getUserId());

        notificationProducer.sendPasswordResetEmail(user.getUserId(), user.getEmail(), resetToken);
        log.info("Password reset email sent to user ID: {}", user.getUserId());
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        log.info("Password reset request received");

        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            log.warn("Password reset failed - Password mismatch");
            throw new PasswordMismatchException();
        }

        UserAuth user = tokenService.validateAndConsumeToken(request.getToken(), TokenType.PASSWORD_RESET);
        log.info("Password reset token validated for user ID: {}", user.getUserId());

        user.setPassword(PasswordEncoderUtil.encode(request.getNewPassword()));
        authRepository.save(user);

        log.info("Password successfully reset for user ID: {}", user.getUserId());
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {
        log.info("Email verification request received");

        UserAuth user = tokenService.validateAndConsumeToken(token, TokenType.EMAIL_VERIFICATION);
        log.info("Email verification token validated for user ID: {}", user.getUserId());

        user.setEmailVerified(true);
        authRepository.save(user);

        log.info("Email verified successfully for user ID: {}", user.getUserId());
    }
}
