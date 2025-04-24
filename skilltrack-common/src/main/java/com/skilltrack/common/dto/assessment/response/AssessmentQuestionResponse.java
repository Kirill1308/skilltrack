package com.skilltrack.common.dto.assessment.response;

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
public class AssessmentQuestionResponse {

    private UUID id;

    private String questionText;

    private String questionType;

    private List<String> options;

    private UUID skillId;

    private String skillName;
}
