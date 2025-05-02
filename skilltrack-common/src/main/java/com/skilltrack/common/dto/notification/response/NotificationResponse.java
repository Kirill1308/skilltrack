package com.skilltrack.common.dto.notification.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private String id;
    private UUID userId;
    private String recipient;
    private String subject;
    private String content;
    private LocalDateTime sentAt;
    private String eventType;
}
