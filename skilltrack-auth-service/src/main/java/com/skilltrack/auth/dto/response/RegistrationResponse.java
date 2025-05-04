package com.skilltrack.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {
    private UUID userId;

    private String email;

    private String firstName;

    private String lastName;

    private Set<String> roles;

    private String department;

    private boolean emailVerified;

    private Instant createdAt;
}
