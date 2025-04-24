package com.skilltrack.common.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String department;

    private String jobTitle;

    private String profilePictureUrl;

    private List<SkillSummaryResponse> skills;

    private List<CertificationSummaryResponse> certifications;

    private Instant createdAt;

    private Instant updatedAt;
}
