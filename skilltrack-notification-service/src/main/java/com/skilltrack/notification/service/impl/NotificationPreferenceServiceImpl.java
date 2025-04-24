package com.skilltrack.notification.service.impl;

import com.skilltrack.common.constant.NotificationType;
import com.skilltrack.notification.model.NotificationPreference;
import com.skilltrack.notification.repository.NotificationPreferenceRepository;
import com.skilltrack.notification.service.NotificationPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationPreferenceServiceImpl implements NotificationPreferenceService {

    private final NotificationPreferenceRepository preferenceRepository;

    @Override
    public NotificationPreference getUserNotificationPreferences(UUID userId) {
        return preferenceRepository.findByUserId(userId)
                .orElseGet(() -> {
                    NotificationPreference defaultPreferences = createDefaultPreferences(userId);
                    return preferenceRepository.save(defaultPreferences);
                });
    }

    @Override
    public NotificationPreference updateUserNotificationPreferences(UUID userId, NotificationPreference preferences) {
        NotificationPreference existingPreferences = preferenceRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultPreferences(userId));

        existingPreferences.setEmailNotificationsEnabled(preferences.isEmailNotificationsEnabled());
        existingPreferences.setPushNotificationsEnabled(preferences.isPushNotificationsEnabled());
        existingPreferences.setInAppNotificationsEnabled(preferences.isInAppNotificationsEnabled());
        existingPreferences.setAssessmentNotificationsEnabled(preferences.isAssessmentNotificationsEnabled());
        existingPreferences.setUserNotificationsEnabled(preferences.isUserNotificationsEnabled());
        existingPreferences.setEnabledTypes(preferences.getEnabledTypes());

        return preferenceRepository.save(existingPreferences);
    }

    private NotificationPreference createDefaultPreferences(UUID userId) {
        NotificationPreference preferences = new NotificationPreference();
        preferences.setId(UUID.randomUUID().toString());
        preferences.setUserId(userId);
        preferences.setEmailNotificationsEnabled(true);
        preferences.setPushNotificationsEnabled(true);
        preferences.setInAppNotificationsEnabled(true);
        preferences.setAssessmentNotificationsEnabled(true);
        preferences.setUserNotificationsEnabled(true);

        // Enable all notification types by default
        preferences.enableNotificationType(NotificationType.EMAIL);
        preferences.enableNotificationType(NotificationType.PUSH);
        preferences.enableNotificationType(NotificationType.IN_APP);
        preferences.enableNotificationType(NotificationType.SMS);

        return preferences;
    }
}
