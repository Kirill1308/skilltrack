package com.skilltrack.auth.controller;

import com.skilltrack.auth.dto.request.ForgotPasswordRequest;
import com.skilltrack.auth.dto.request.LoginRequest;
import com.skilltrack.auth.dto.request.RegistrationRequest;
import com.skilltrack.auth.dto.request.ResetPasswordRequest;
import com.skilltrack.auth.dto.request.TokenRefreshRequest;
import com.skilltrack.auth.dto.response.AuthenticationResponse;
import com.skilltrack.auth.dto.response.RegistrationResponse;
import com.skilltrack.auth.dto.response.TokenRefreshResponse;
import com.skilltrack.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegistrationResponse registerUser(@Valid @RequestBody RegistrationRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse loginUser(@Valid @RequestBody LoginRequest request) {
        return authService.loginUser(request);
    }

    @PostMapping("/refresh-token")
    public TokenRefreshResponse refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.initiatePasswordReset(request);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
    }

    @PostMapping("/verify-email/{token}")
    public void verifyEmail(@PathVariable String token) {
        authService.verifyEmail(token);
    }

}
