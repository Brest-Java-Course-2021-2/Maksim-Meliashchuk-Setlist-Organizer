package com.epam.brest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"spring.cloud.config.enabled:false"})
class RestApplicationTest {
    @Test
    public void contextLoads() {
    }

}