package com.skilltrack.common.dto.skill.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillUpdateRequest {

    @NotNull(message = "Proficiency level is required")
    private Integer proficiencyLevel;

    private String notes;

    private List<UUID> evidenceIds;
}
