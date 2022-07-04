package com.epam.brest.notificationapp.config;

import com.epam.brest.notificationapp.service.api.NotificationWebSocketService;
import com.epam.brest.notificationapp.service.impl.NotificationWebSocketServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "yaml")
@PropertySource(value = "classpath:websocket-messages.yaml", factory = YamlPropertySourceFactory.class)
public class ApplicationConfig {
    @Bean
    public NotificationWebSocketService notificationWebSocketService() {
        return new NotificationWebSocketServiceImpl();
    }
}
