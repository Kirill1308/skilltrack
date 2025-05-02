package com.skilltrack.notification.service.impl;

import com.skilltrack.common.dto.notification.request.NotificationRequest;
import com.skilltrack.common.dto.notification.response.NotificationResponse;
import com.skilltrack.notification.exception.NotificationNotFoundException;
import com.skilltrack.notification.model.Notification;
import com.skilltrack.notification.repository.NotificationRepository;
import com.skilltrack.notification.service.EmailService;
import com.skilltrack.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final EmailService emailService;
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationResponse createNotification(NotificationRequest request) {
        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .senderId(UUID.fromString(request.getSenderId()))
                .recipient(request.getRecipient())
                .subject(request.getSubject())
                .content(request.getContent())
                .eventType(request.getEventType())
                .build();

        Notification savedNotification = notificationRepository.save(notification);

        sendNotification(savedNotification);

        return mapToResponse(savedNotification);
    }

    @Override
    public Page<NotificationResponse> getUserNotifications(UUID userId, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findBySenderIdOrderBySentAtDesc(userId, pageable);
        return notifications.map(this::mapToResponse);
    }

    @Override
    public NotificationResponse getNotificationById(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));

        return mapToResponse(notification);
    }

    @Override
    public void deleteNotification(String id) {
        if (!notificationRepository.existsById(id)) {
            throw new NotificationNotFoundException(id);
        }

        notificationRepository.deleteById(id);
    }

    private void sendNotification(Notification notification) {
        emailService.sendEmailNotification(notification);
        notification.markAsSent();
        notificationRepository.save(notification);
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getSenderId())
                .recipient(notification.getRecipient())
                .subject(notification.getSubject())
                .content(notification.getContent())
                .sentAt(notification.getSentAt())
                .eventType(notification.getEventType())
                .build();
    }
}
