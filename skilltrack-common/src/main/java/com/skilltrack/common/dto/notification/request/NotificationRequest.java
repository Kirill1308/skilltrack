package com.skilltrack.common.dto.notification.request;

import com.skilltrack.common.validation.annotation.ValidUUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotNull(message = "Sender ID is required")
    @ValidUUID(message = "Sender ID must be a valid UUID")
    private String senderId;

    @NotBlank(message = "Recipient is required")
    private String recipient;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Content is required")
    private String content;

    private String eventType;

}
