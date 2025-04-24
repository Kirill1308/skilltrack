package com.skilltrack.common.messaging.event;

import com.skilltrack.common.constant.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent extends BaseEvent {
    private UUID userId;
    private String recipient;
    private String subject;
    private String content;
    private NotificationType type;
}
