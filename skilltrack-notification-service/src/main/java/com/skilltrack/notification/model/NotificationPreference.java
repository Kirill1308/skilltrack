package com.skilltrack.notification.model;

import com.skilltrack.common.constant.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document(collection = "notification_preferences")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreference {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field(name = "user_id")
    private UUID userId;

    @Field(name = "enabled_types")
    private Set<NotificationType> enabledTypes = new HashSet<>();

    @Field(name = "email_notifications_enabled")
    private boolean emailNotificationsEnabled = true;

    @Field(name = "push_notifications_enabled")
    private boolean pushNotificationsEnabled = true;

    @Field(name = "in_app_notifications_enabled")
    private boolean inAppNotificationsEnabled = true;

    @Field(name = "assessment_notifications_enabled")
    private boolean assessmentNotificationsEnabled = true;

    @Field(name = "user_notifications_enabled")
    private boolean userNotificationsEnabled = true;

    public boolean isNotificationTypeEnabled(NotificationType type) {
        return enabledTypes.contains(type);
    }

    public void enableNotificationType(NotificationType type) {
        enabledTypes.add(type);
    }

    public void disableNotificationType(NotificationType type) {
        enabledTypes.remove(type);
    }
}
