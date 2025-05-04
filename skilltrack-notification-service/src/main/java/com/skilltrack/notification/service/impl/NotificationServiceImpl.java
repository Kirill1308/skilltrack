package com.skilltrack.notification.service.impl;

import com.skilltrack.common.dto.notification.request.NotificationRequest;
import com.skilltrack.common.dto.notification.response.NotificationResponse;
import com.skilltrack.notification.exception.NotificationNotFoundException;
import com.skilltrack.notification.mapper.NotificationMapper;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final EmailService emailService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public NotificationResponse createNotification(NotificationRequest request) {
        log.info("Creating notification: type={}, recipient={}", request.getEventType(), request.getRecipient());

        Notification notification = notificationMapper.toEntity(request);
        log.debug("Mapped notification request to entity with ID: {}", notification.getId());

        Notification savedNotification = notificationRepository.save(notification);
        log.info("Notification saved successfully with ID: {}", savedNotification.getId());

        sendNotification(savedNotification);

        NotificationResponse response = notificationMapper.toResponse(savedNotification);
        log.info("Notification creation completed successfully for ID: {}", savedNotification.getId());

        return response;
    }

    @Override
    public Page<NotificationResponse> getUserNotifications(UUID userId, Pageable pageable) {
        log.info("Retrieving notifications for user ID: {}, page: {}, size: {}", userId, pageable.getPageNumber(), pageable.getPageSize());

        Page<Notification> notifications = notificationRepository.findBySenderIdOrderBySentAtDesc(userId, pageable);
        log.info("Found {} notifications for user ID: {}", notifications.getTotalElements(), userId);

        Page<NotificationResponse> responses = notifications.map(notificationMapper::toResponse);
        log.debug("Successfully mapped {} notifications to response objects", responses.getNumberOfElements());

        return responses;
    }

    @Override
    public NotificationResponse getNotificationById(String id) {
        log.info("Retrieving notification by ID: {}", id);

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Notification not found with ID: {}", id);
                    return new NotificationNotFoundException(id);
                });

        log.debug("Notification retrieved successfully for ID: {}", id);

        return notificationMapper.toResponse(notification);
    }

    @Override
    public void deleteNotification(String id) {
        log.info("Deleting notification with ID: {}", id);

        if (!notificationRepository.existsById(id)) {
            log.warn("Cannot delete notification - not found with ID: {}", id);
            throw new NotificationNotFoundException(id);
        }

        notificationRepository.deleteById(id);
        log.info("Notification deleted successfully with ID: {}", id);
    }

    private void sendNotification(Notification notification) {
        log.info("Sending notification with ID: {} to recipient: {}", notification.getId(), notification.getRecipient());

        emailService.sendEmailNotification(notification);
        log.info("Email sent successfully for notification ID: {}", notification.getId());

        notification.markAsSent();
        notificationRepository.save(notification);
        log.info("Notification marked as sent and updated with ID: {}", notification.getId());
    }
}
