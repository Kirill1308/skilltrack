package com.skilltrack.common.dto.assessment.request;

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
public class AssessmentAnswerRequest {

    @NotNull(message = "Question ID is required")
    private UUID questionId;

    private String textAnswer;

    private List<String> selectedOptions;

    private Integer rating;
}
