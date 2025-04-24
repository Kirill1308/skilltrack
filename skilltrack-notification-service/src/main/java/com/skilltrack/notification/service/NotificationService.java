package com.skilltrack.notification.service;

import com.skilltrack.common.constant.NotificationType;
import com.skilltrack.common.dto.notification.request.NotificationRequest;
import com.skilltrack.common.dto.notification.response.NotificationResponse;
import com.skilltrack.notification.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationService {

    NotificationResponse createNotification(NotificationRequest request);

    Page<NotificationResponse> getUserNotifications(UUID userId, boolean unreadOnly, Pageable pageable);

    NotificationResponse getNotificationById(String id);

    void markAsRead(String id);

    void markAllAsRead(UUID userId);

    void deleteNotification(String id);

    boolean isNotificationEnabled(UUID userId, NotificationType type);
}
