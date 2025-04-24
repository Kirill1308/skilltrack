package com.skilltrack.common.dto.assessment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentSubmissionResponse {
    private String id;
    private String assessmentId;
    private String userId;
    private String status;
    private String submissionDate;
    private String score;
    private String feedback;
}
