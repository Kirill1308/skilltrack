package com.skilltrack.notification.consumer;

import com.skilltrack.common.dto.notification.request.NotificationRequest;
import com.skilltrack.common.messaging.event.NotificationEvent;
import com.skilltrack.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${messaging.queues.notification}")
    public void consumeNotification(NotificationEvent event) {
        log.info("Received notification event: {}", event);

        try {
            NotificationRequest request = NotificationRequest.builder()
                    .senderId(event.getSenderId().toString())
                    .recipient(event.getRecipient())
                    .subject(event.getSubject())
                    .content(event.getContent())
                    .type(event.getType())
                    .eventType(event.getEventType())
                    .build();

            notificationService.createNotification(request);

            log.info("Successfully processed notification event: {}", event.getEventId());
        } catch (Exception e) {
            log.error("Error processing notification event: {}", event, e);
            // The message will be sent to the dead letter queue due to our configuration
            throw e; // Re-throw to trigger the DLQ mechanism
        }
    }

    @RabbitListener(queues = "${messaging.queues.notification-dead-letter}")
    public void handleDeadLetterNotification(NotificationEvent event) {
        log.error("Processing dead letter notification: {}", event);
        // Implement logic for handling failed messages
        // Options include:
        // 1. Store in database for manual review
        // 2. Log detailed information
        // 3. Trigger alerts
        // 4. Attempt remediation
    }
}
