package com.skilltrack.notification.model;

import com.skilltrack.common.constant.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private String id;

    @Field(name = "sender_id")
    private UUID senderId;

    private String recipient;

    private String subject;

    private String content;

    private NotificationType type;

    @Field(name = "sent_at")
    private LocalDateTime sentAt;

    @Field(name = "read_at")
    private LocalDateTime readAt;

    @Field(name = "is_read")
    private boolean read;

    @Field(name = "event_type")
    private String eventType;

    @Field(name = "reference_id")
    private UUID referenceId;

    public void markAsRead() {
        this.read = true;
        this.readAt = LocalDateTime.now();
    }

    public void markAsSent() {
        this.sentAt = LocalDateTime.now();
    }
}
