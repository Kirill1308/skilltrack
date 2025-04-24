package com.skilltrack.common.dto.skill.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillUpdateRequest {

    @Size(max = 100, message = "Skill name must not exceed 100 characters")
    private String name;

    private String category;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private List<String> tags;

    private String verificationCriteria;
}
