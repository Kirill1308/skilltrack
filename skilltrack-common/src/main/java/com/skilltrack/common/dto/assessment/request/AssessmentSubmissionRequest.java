package com.skilltrack.common.dto.assessment.request;

import jakarta.validation.constraints.NotEmpty;
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
public class AssessmentSubmissionRequest {

    @NotNull(message = "Assessment ID is required")
    private UUID assessmentId;

    @NotEmpty(message = "Answers are required")
    private List<AssessmentAnswerRequest> answers;

    private String feedback;
}
