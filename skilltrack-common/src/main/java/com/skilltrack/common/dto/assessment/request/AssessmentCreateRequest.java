package com.skilltrack.common.dto.assessment.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class AssessmentCreateRequest {

    @NotBlank(message = "Assessment title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotEmpty(message = "At least one skill must be selected")
    private List<UUID> skillIds;

    @NotEmpty(message = "At least one user must be assigned")
    private List<UUID> assigneeIds;

    @Future(message = "Due date must be in the future")
    private Instant dueDate;

    private String assessmentType;

    private List<AssessmentQuestionRequest> questions;
}
