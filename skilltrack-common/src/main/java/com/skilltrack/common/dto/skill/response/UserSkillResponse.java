package com.skilltrack.common.dto.skill.response;

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
public class UserSkillResponse {

    private UUID id;

    private UUID userId;

    private UUID skillId;

    private String skillName;

    private String skillCategory;

    private Integer proficiencyLevel;

    private String notes;

    private List<EvidenceResponse> evidence;

    private List<EndorsementResponse> endorsements;

    private Boolean isVerified;

    private Instant createdAt;

    private Instant updatedAt;
}
