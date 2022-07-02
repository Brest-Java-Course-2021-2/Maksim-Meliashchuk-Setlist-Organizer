package com.epam.brest.notificationapp.events;


import com.epam.brest.model.kafka.RepertoireEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.function.Consumer;

@Slf4j
@Configuration
@AllArgsConstructor
public class RepertoireChangeHandler {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Bean
    public Consumer<RepertoireEvent> consumer() {
        return event -> {
            var message = event.getEventType() + " " + event.getTrack().getTrackName();
            log.info("received repertoire message='{}'", message);
            simpMessagingTemplate.convertAndSend("/topic/repertoire", message);
        };
    }
}
