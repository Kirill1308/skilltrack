package com.skilltrack.user.mapper;

import com.skilltrack.common.dto.user.response.SkillSummaryResponse;
import com.skilltrack.user.model.UserSkill;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class SkillMapper {

    public List<SkillSummaryResponse> toSummaryResponseList(Set<UserSkill> skills) {
        if (skills == null || skills.isEmpty()) {
            return List.of();
        }

        return skills.stream()
                .map(skill -> SkillSummaryResponse.builder()
                        .id(skill.getSkill().getId())
                        .name(skill.getSkill().getName())
                        .category(skill.getSkill().getCategory().name())
                        .proficiencyLevel(skill.getProficiencyLevel().name())
                        .build())
                .toList();
    }
}
