package com.skilltrack.common.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationSummaryResponse {

    private UUID id;

    private String name;

    private String issuingOrganization;

    private Instant issueDate;

    private Instant expiryDate;
}
