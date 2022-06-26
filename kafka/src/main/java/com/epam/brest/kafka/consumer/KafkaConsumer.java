package com.epam.brest.kafka.consumer;

import com.epam.brest.model.Track;
import com.epam.brest.model.kafka.EventType;
import com.epam.brest.model.kafka.RepertoireEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@Component
@ConditionalOnProperty(value="kafka.consumer.enabled", matchIfMissing = false)
public class KafkaConsumer {

    private Track track;
    private EventType eventType;

    @KafkaListener(topics = "${kafka.topics.repertoire-changed}")
    public void receiveRepertoireEvent(RepertoireEvent repertoireEvent) {
        log.info("received repertoire changed message ='{}'", repertoireEvent.toString());
        eventType = repertoireEvent.getEventType();
        track = repertoireEvent.getTrack();
    }

}
