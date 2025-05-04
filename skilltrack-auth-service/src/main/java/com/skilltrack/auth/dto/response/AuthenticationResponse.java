package com.skilltrack.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String accessToken;

    private String refreshToken;

    private UUID userId;

    private String firstName;

    private String lastName;

    private String email;

    private Set<String> roles;

}
