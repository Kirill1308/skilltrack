package com.skilltrack.notification.service;

import com.skilltrack.notification.model.NotificationPreference;

import java.util.UUID;

public interface NotificationPreferenceService {

    NotificationPreference getUserNotificationPreferences(UUID userId);

    NotificationPreference updateUserNotificationPreferences(UUID userId, NotificationPreference request);
}
