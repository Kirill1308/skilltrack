package com.skilltrack.auth.service;

import com.skilltrack.auth.dto.request.ForgotPasswordRequest;
import com.skilltrack.auth.dto.request.LoginRequest;
import com.skilltrack.auth.dto.request.RegistrationRequest;
import com.skilltrack.auth.dto.request.ResetPasswordRequest;
import com.skilltrack.auth.dto.request.TokenRefreshRequest;
import com.skilltrack.auth.dto.response.AuthenticationResponse;
import com.skilltrack.auth.dto.response.RegistrationResponse;
import com.skilltrack.auth.dto.response.TokenRefreshResponse;

public interface AuthService {
    RegistrationResponse registerUser(RegistrationRequest request);

    AuthenticationResponse loginUser(LoginRequest request);

    TokenRefreshResponse refreshToken(TokenRefreshRequest request);

    void initiatePasswordReset(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

    void verifyEmail(String token);
}
