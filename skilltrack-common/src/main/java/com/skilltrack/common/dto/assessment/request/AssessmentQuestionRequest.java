package com.skilltrack.common.dto.assessment.request;

import jakarta.validation.constraints.NotBlank;
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
public class AssessmentQuestionRequest {

    @NotBlank(message = "Question text is required")
    private String questionText;

    private String questionType;

    private List<String> options;

    private UUID skillId;
}
