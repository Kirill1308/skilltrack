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
public class EndorsementResponse {

    private UUID id;

    private UUID endorserId;

    private String endorserName;

    private String relationship;

    private String comments;

    private Instant endorsedAt;
}
