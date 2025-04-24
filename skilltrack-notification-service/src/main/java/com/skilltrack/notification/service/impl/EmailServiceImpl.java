package com.skilltrack.notification.service.impl;

import com.skilltrack.notification.config.props.EmailProperties;
import com.skilltrack.notification.model.Notification;
import com.skilltrack.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailProperties mailProperties;

    @Override
    public void sendEmailNotification(Notification notification) {
        String subject = notification.getSubject();
        String message = notification.getContent();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(mailProperties.getUsername());
        email.setTo(notification.getRecipient());
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}
