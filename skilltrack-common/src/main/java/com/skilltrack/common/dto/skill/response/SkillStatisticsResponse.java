package com.skilltrack.common.dto.skill.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillStatisticsResponse {

    private Long totalSkills;

    private Long totalUserSkills;

    private List<CategoryDistributionItem> categoryDistribution;

    private List<ProficiencyDistributionItem> proficiencyDistribution;
}
