package com.skilltrack.notification.model;

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

    @Field(name = "sent_at")
    private LocalDateTime sentAt;

    @Field(name = "event_type")
    private String eventType;

    public void markAsSent() {
        this.sentAt = LocalDateTime.now();
    }
}
