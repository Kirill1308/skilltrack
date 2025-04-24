package com.skilltrack.notification.service;

import com.skilltrack.notification.model.Notification;

public interface PushNotificationService {
    void sendPushNotification(Notification notification);
}
