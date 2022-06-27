package com.epam.brest.producer;

import com.epam.brest.model.kafka.RepertoireEvent;
import com.epam.brest.service.kafka.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("!nokafka")
public class KafkaProducerService implements ProducerService {

    private final KafkaTemplate<Integer, RepertoireEvent> kafkaTemplate;

    @Value("${kafka.topics.repertoire-changed}")
    private String repertoireChangedTopic;

    public void sendRepertoireMessage(String topic, RepertoireEvent repertoireEvent) {

        ListenableFuture<SendResult<Integer, RepertoireEvent>> future = kafkaTemplate
                .send(topic, repertoireEvent.getTrack().getTrackId(), repertoireEvent);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<Integer, RepertoireEvent> result) {
                log.info("sent repertoire message='{}' with offset={}", repertoireEvent.getEventType() + " " +
                        repertoireEvent.getTrack().getTrackName(), result.getRecordMetadata()
                        .offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("unable to send repertoire message='{}'", repertoireEvent.getEventType() + " " +
                        repertoireEvent.getTrack().getTrackName(), ex);
            }
        });
    }
}
