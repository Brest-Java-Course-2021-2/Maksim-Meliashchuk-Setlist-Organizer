package com.epam.brest.web_app.starter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CustomContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @SneakyThrows
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        URL url = new URL(Objects
                .requireNonNull(environment.getProperty("spring.security.oauth2.client.provider.keycloak.issuer-uri")));
        int numberOfConnectionAttempts = Integer.parseInt(Objects
                .requireNonNull(environment.getProperty("spring.security.oauth2.client.provider.keycloak.number-of-connection-attempts")));
        int timeoutBetweenConnectionAttempts = Integer.parseInt(Objects
                .requireNonNull(environment.getProperty("spring.security.oauth2.client.provider.keycloak.timeout-between-connection-attempts")));

        int attemptsCount = 0;
        while (attemptsCount < numberOfConnectionAttempts) {
            attemptsCount++;
            try {
                TimeUnit.SECONDS.sleep(timeoutBetweenConnectionAttempts);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                if (huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    log.debug(url + " connection successful");
                    break;
                }
            } catch (Exception e) {
                log.debug(url + " connection refused");
            }
        }
        if (attemptsCount == numberOfConnectionAttempts) {
            log.debug(url + " attempts connections timeout");
            System.exit(0);
        }
    }
}
