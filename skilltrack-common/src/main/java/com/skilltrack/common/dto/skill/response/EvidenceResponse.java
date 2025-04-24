package com.skilltrack.common.dto.skill.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvidenceResponse {

    private UUID id;

    private String title;

    private String description;

    private String evidenceType;

    private String url;

    private Instant date;
}
