package com.skilltrack.notification.service;

import com.skilltrack.notification.model.Notification;

public interface EmailService {
    void sendEmailNotification(Notification notification);
}
