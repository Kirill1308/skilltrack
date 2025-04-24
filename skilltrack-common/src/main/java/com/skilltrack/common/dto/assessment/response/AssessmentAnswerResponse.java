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
public class AssessmentAnswerResponse {

    private UUID id;

    private UUID questionId;

    private String questionText;

    private String textAnswer;

    private List<String> selectedOptions;

    private Integer rating;

    private Double score;

    private String reviewerFeedback;
}
