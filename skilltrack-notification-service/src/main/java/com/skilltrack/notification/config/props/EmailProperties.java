package com.skilltrack.notification.config.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.mail")
public class EmailProperties {

    private String host;
    private int port;
    private String username;
    private String password;
    private Properties properties;

}
