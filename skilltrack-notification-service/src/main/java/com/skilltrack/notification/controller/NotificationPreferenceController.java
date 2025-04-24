package com.skilltrack.notification.controller;

import com.skilltrack.notification.model.NotificationPreference;
import com.skilltrack.notification.service.NotificationPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class NotificationPreferenceController {

    private final NotificationPreferenceService preferenceService;

    @GetMapping("/users/{userId}")
    public NotificationPreference getUserPreferences(@PathVariable UUID userId) {
        return preferenceService.getUserNotificationPreferences(userId);
    }

    @PutMapping("/{id}")
    public NotificationPreference updatePreference(
            @PathVariable UUID id,
            @Valid @RequestBody NotificationPreference request
    ) {
        return preferenceService.updateUserNotificationPreferences(id, request);
    }
}
