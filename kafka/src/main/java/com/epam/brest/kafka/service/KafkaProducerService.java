package com.epam.brest.kafka.service;

import com.epam.brest.kafka.model.RepertoireEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<Integer, RepertoireEvent> kafkaTemplate;

    public void sendRepertoireMessage(String topic, RepertoireEvent repertoireEvent) {

        ListenableFuture<SendResult<Integer, RepertoireEvent>> future = kafkaTemplate
                .send(topic, repertoireEvent.getTrack().getTrackId(), repertoireEvent);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<Integer, RepertoireEvent> result) {
                log.info("sent repertoire message='{}' with offset={}", repertoireEvent, result.getRecordMetadata()
                        .offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("unable to send repertoire message='{}'", repertoireEvent, ex);
            }
        });
    }
}
