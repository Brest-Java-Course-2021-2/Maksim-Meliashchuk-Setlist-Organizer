package com.epam.brest.kafka.consumer;

import com.epam.brest.kafka.model.EventType;
import com.epam.brest.kafka.model.RepertoireEvent;
import com.epam.brest.model.Track;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@Component
public class KafkaConsumer {

    private Track track;
    private EventType eventType;

    @KafkaListener(topics = "${kafka.topics.repertoire-changed}")
    public void receive(RepertoireEvent repertoireEvent) {
        log.info("received repertoire changed message ='{}'", repertoireEvent.toString());
        eventType = repertoireEvent.getEventType();
        track = repertoireEvent.getTrack();
    }

}
