package com.skilltrack.common.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillSummaryResponse {

    private UUID id;

    private String name;

    private String category;

    private String proficiencyLevel;
}
