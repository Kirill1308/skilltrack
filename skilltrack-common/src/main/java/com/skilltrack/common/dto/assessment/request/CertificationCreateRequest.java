package com.skilltrack.common.dto.assessment.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CertificationCreateRequest {

    @NotBlank(message = "Certification name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Issuing organization is required")
    private String issuingOrganization;

    @NotNull(message = "Issue date is required")
    private Instant issueDate;

    private Instant expiryDate;

    private String credentialId;

    private String credentialUrl;

    private List<UUID> relatedSkillIds;
}
