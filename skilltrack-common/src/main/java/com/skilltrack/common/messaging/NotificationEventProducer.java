package com.skilltrack.common.messaging;

import com.skilltrack.common.constant.NotificationType;
import com.skilltrack.common.messaging.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationEventProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-keys.notification}")
    private String notificationRoutingKey;

    public void sendVerificationEmail(UUID userId, String email, String token) {
        String subject = "SkillTrack - Email Verification";
        String message = String.format(
                "Hello,%n%n" +
                "Please verify your email address by entering the verification code below:%n%n" +
                "CODE: %s%n%n" +
                "Enter this code on the verification page to complete your registration.%n%n" +
                "This code will expire in 7 days.%n%n" +
                "If you did not create an account, please ignore this email.%n%n" +
                "Thanks,%n" +
                "The SkillTrack Team",
                token
        );

        NotificationEvent event = NotificationEvent.builder()
                .eventId(UUID.randomUUID())
                .userId(userId)
                .recipient(email)
                .subject(subject)
                .content(message)
                .type(NotificationType.EMAIL)
                .eventType("EMAIL_VERIFICATION")
                .referenceId(userId)
                .timestamp(Instant.now())
                .build();

        sendNotificationEvent(event);
    }

    public void sendPasswordResetEmail(UUID userId, String email, String token) {
        String subject = "SkillTrack - Password Reset Code";
        String message = String.format(
                "Hello,%n%n" +
                "You recently requested to reset your password.%n%n" +
                "Your password reset code is: %s%n%n" +
                "Enter this code on the password reset page to continue.%n%n" +
                "This code will expire in 24 hours.%n%n" +
                "If you did not request a password reset, please ignore this email or contact support if you have concerns.%n%n" +
                "Thanks,%n" +
                "The SkillTrack Team",
                token
        );

        NotificationEvent event = NotificationEvent.builder()
                .eventId(UUID.randomUUID())
                .userId(userId)
                .recipient(email)
                .subject(subject)
                .content(message)
                .type(NotificationType.EMAIL)
                .eventType("PASSWORD_RESET")
                .referenceId(userId)
                .timestamp(Instant.now())
                .build();

        sendNotificationEvent(event);
    }

    private void sendNotificationEvent(NotificationEvent event) {
        try {
            rabbitTemplate.convertAndSend(exchange, notificationRoutingKey, event);
            log.debug("Sent notification event: {}", event.getEventId());
        } catch (Exception e) {
            log.error("Failed to send notification event: {}", event.getEventId(), e);
        }
    }
}
