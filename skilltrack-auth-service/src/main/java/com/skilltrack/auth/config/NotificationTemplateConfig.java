package com.skilltrack.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "notification.templates")
@Getter
@Setter
public class NotificationTemplateConfig {
    private Template verification;
    private Template passwordReset;

    @Getter
    @Setter
    public static class Template {
        private String subject;
        private String content;
    }
}
