package com.skilltrack.notification.mapper;

import com.skilltrack.common.dto.notification.request.NotificationRequest;
import com.skilltrack.common.dto.notification.response.NotificationResponse;
import com.skilltrack.notification.model.Notification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotificationMapper {

    public Notification toEntity(NotificationRequest request) {
        return Notification.builder()
                .id(UUID.randomUUID().toString())
                .senderId(UUID.fromString(request.getSenderId()))
                .recipient(request.getRecipient())
                .subject(request.getSubject())
                .content(request.getContent())
                .eventType(request.getEventType())
                .build();
    }

    public NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .senderId(notification.getSenderId())
                .recipient(notification.getRecipient())
                .subject(notification.getSubject())
                .content(notification.getContent())
                .sentAt(notification.getSentAt())
                .eventType(notification.getEventType())
                .build();
    }
}
