package com.epam.brest.web_app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"spring.security.oauth2.client.provider.keycloak.pre-connection-check: false"})
class SetlistOrganizerApplicationTest {
    @Test
    public void contextLoads() {
    }
}