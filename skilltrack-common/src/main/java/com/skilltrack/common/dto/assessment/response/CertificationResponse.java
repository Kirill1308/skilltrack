package com.skilltrack.common.dto.assessment.response;

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
public class CertificationResponse {

    private UUID id;

    private String name;

    private String issuingOrganization;

    private Instant issuedAt;

    private Instant expiresAt;

    private String credentialId;

    private String credentialUrl;

    private List<SkillSummaryResponse> relatedSkills;

    private UUID userId;

    private Instant createdAt;

    private Instant updatedAt;
}
