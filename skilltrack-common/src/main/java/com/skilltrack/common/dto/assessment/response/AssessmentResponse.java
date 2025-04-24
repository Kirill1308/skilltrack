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
public class AssessmentResponse {

    private UUID id;

    private String title;

    private String description;

    private List<SkillSummaryResponse> skills;

    private UUID createdBy;

    private String creatorName;

    private List<AssigneeResponse> assignees;

    private Instant dueDate;

    private String assessmentType;

    private String status;

    private Instant createdAt;

    private Instant updatedAt;
}
