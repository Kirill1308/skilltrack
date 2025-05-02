package com.skilltrack.notification.service;

import com.skilltrack.common.dto.notification.request.NotificationRequest;
import com.skilltrack.common.dto.notification.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationService {

    NotificationResponse createNotification(NotificationRequest request);

    Page<NotificationResponse> getUserNotifications(UUID userId, Pageable pageable);

    NotificationResponse getNotificationById(String id);

    void deleteNotification(String id);

}
