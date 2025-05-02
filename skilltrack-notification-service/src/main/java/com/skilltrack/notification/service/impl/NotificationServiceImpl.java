package com.skilltrack.notification.service.impl;

import com.skilltrack.common.constant.NotificationType;
import com.skilltrack.common.dto.notification.request.NotificationRequest;
import com.skilltrack.common.dto.notification.response.NotificationResponse;
import com.skilltrack.notification.exception.NotificationNotFoundException;
import com.skilltrack.notification.model.Notification;
import com.skilltrack.notification.model.NotificationPreference;
import com.skilltrack.notification.repository.NotificationRepository;
import com.skilltrack.notification.service.EmailService;
import com.skilltrack.notification.service.NotificationPreferenceService;
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

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final NotificationPreferenceService preferenceService;

    @Override
    public NotificationResponse createNotification(NotificationRequest request) {
        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .senderId(UUID.fromString(request.getSenderId()))
                .recipient(request.getRecipient())
                .subject(request.getSubject())
                .content(request.getContent())
                .type(request.getType())
                .eventType(request.getEventType())
                .read(false)
                .build();

        Notification savedNotification = notificationRepository.save(notification);

        if (isNotificationEnabled(UUID.fromString(request.getSenderId()), request.getType())) {
            sendNotification(savedNotification);
        }

        return mapToResponse(savedNotification);
    }

    @Override
    public Page<NotificationResponse> getUserNotifications(UUID userId, boolean unreadOnly, Pageable pageable) {
        Page<Notification> notifications;

        if (unreadOnly) {
            notifications = notificationRepository.findBySenderIdAndReadOrderBySentAtDesc(userId, false, pageable);
        } else {
            notifications = notificationRepository.findBySenderIdOrderBySentAtDesc(userId, pageable);
        }

        return notifications.map(this::mapToResponse);
    }

    @Override
    public NotificationResponse getNotificationById(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));

        return mapToResponse(notification);
    }

    @Override
    public void markAsRead(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));

        notification.markAsRead();
        notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead(UUID userId) {
        notificationRepository.markAllAsRead(userId);
    }

    @Override
    public void deleteNotification(String id) {
        if (!notificationRepository.existsById(id)) {
            throw new NotificationNotFoundException(id);
        }

        notificationRepository.deleteById(id);
    }

    @Override
    public boolean isNotificationEnabled(UUID userId, NotificationType type) {
        NotificationPreference preferences = preferenceService.getUserNotificationPreferences(userId);

        boolean typeEnabled = preferences.isNotificationTypeEnabled(type);

        return switch (type) {
            case EMAIL -> typeEnabled && preferences.isEmailNotificationsEnabled();
            case PUSH -> typeEnabled && preferences.isPushNotificationsEnabled();
            case IN_APP -> typeEnabled && preferences.isInAppNotificationsEnabled();
            case SMS -> typeEnabled; // No specific toggle for SMS yet
        };
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
                .type(notification.getType())
                .sentAt(notification.getSentAt())
                .readAt(notification.getReadAt())
                .read(notification.isRead())
                .eventType(notification.getEventType())
                .build();
    }
}
