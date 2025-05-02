package com.skilltrack.auth.messaging;

import com.skilltrack.auth.config.NotificationTemplateConfig;
import com.skilltrack.common.messaging.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationEventProducer {

    private final RabbitTemplate rabbitTemplate;
    private final NotificationTemplateConfig config;

    @Value("${messaging.routing-keys.user-created}")
    private String userCreatedRoutingKey;

    @Value("${messaging.routing-keys.password-reset}")
    private String passwordResetRoutingKey;

    public void sendVerificationEmail(UUID userId, String email, String token) {
        String subject = config.getVerification().getSubject();
        String message = MessageFormat.format(config.getVerification().getContent(), token);

        NotificationEvent event = NotificationEvent.builder()
                .eventId(UUID.randomUUID())
                .senderId(userId)
                .recipient(email)
                .subject(subject)
                .content(message)
                .eventType("EMAIL_VERIFICATION")
                .timestamp(Instant.now())
                .build();

        sendNotificationEvent(event, userCreatedRoutingKey);
    }

    public void sendPasswordResetEmail(UUID userId, String email, String token) {
        String subject = config.getPasswordReset().getSubject();
        String message = MessageFormat.format(config.getPasswordReset().getContent(), token);

        NotificationEvent event = NotificationEvent.builder()
                .eventId(UUID.randomUUID())
                .senderId(userId)
                .recipient(email)
                .subject(subject)
                .content(message)
                .eventType("PASSWORD_RESET")
                .timestamp(Instant.now())
                .build();

        sendNotificationEvent(event, passwordResetRoutingKey);
    }

    private void sendNotificationEvent(NotificationEvent event, String routingKey) {
        try {
            rabbitTemplate.convertAndSend(routingKey, event);
            log.debug("Sent notification event: {}", event.getEventId());
        } catch (Exception e) {
            log.error("Failed to send notification event: {}", event.getEventId(), e);
        }
    }
}
