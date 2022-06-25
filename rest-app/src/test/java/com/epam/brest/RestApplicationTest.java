package com.epam.brest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"spring.cloud.config.enabled:false"})
@TestPropertySource(properties = {"eureka.client.enabled:false"})
@EmbeddedKafka(partitions = 1, topics = {"repertoire_changed"}, ports = 9092)
class RestApplicationTest {
        @Test
    public void contextLoads() {
    }

}