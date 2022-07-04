package com.epam.brest.config;

import com.epam.brest.model.kafka.RepertoireEvent;
import com.epam.brest.service.kafka.ProducerService;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationConfig {

    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
    }

    @Bean
    @Profile("nokafka")
    public ProducerService producerServiceEmpty() {
        return new EmptyProducerService();
    }

    public static class EmptyProducerService implements ProducerService {
        @Override
        public void sendRepertoireMessage(String topic, RepertoireEvent repertoireEvent) {

        }
    }

}
