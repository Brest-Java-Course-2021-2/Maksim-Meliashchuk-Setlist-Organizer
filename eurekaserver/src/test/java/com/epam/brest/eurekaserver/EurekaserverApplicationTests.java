package com.epam.brest.eurekaserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"spring.cloud.config.enabled:false"})
class EurekaserverApplicationTests {

	@Test
	void contextLoads() {
	}

}
