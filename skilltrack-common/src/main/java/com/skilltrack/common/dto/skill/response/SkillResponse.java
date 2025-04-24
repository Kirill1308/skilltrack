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
public class SkillResponse {

    private UUID id;

    private String name;

    private String category;

    private String description;

    private List<String> tags;

    private String verificationCriteria;

    private Integer usersCount;

    private Instant createdAt;

    private Instant updatedAt;
}
