package com.epam.brest.kafka.service;

import com.epam.brest.kafka.model.EventType;
import com.epam.brest.kafka.model.RepertoireEvent;
import com.epam.brest.model.Track;
import config.KafkaTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ContextConfiguration(classes = KafkaTestConfig.class)
@DirtiesContext
@ExtendWith(SpringExtension.class)
@EmbeddedKafka(partitions = 1, topics = {"repertoire_changed"}, ports = 9092)
class KafkaProducerServiceIT {

    private static final String TOPIC_NAME = "repertoire_changed";
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaProducerService producerService;

    @Test
    void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenSendRepertoireMessage() {

        log.debug("givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenSendRepertoireMessage()");

        var track = Track.builder().trackId(1).trackName("Track").build();
        var repertoireEvent = RepertoireEvent.builder()
                .track(track)
                .eventType(EventType.CREATE_TRACK)
                .build();

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("group_consumer_test", "false", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        ConsumerFactory<String, RepertoireEvent> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps,
                new StringDeserializer(), new JsonDeserializer<>(RepertoireEvent.class, false));
        Consumer<String, RepertoireEvent> consumerServiceTest = consumerFactory.createConsumer();

        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumerServiceTest, TOPIC_NAME);

        producerService.sendRepertoireMessage(TOPIC_NAME, repertoireEvent);

        ConsumerRecord<String, RepertoireEvent> consumerRecordOfRepertoireEvent = KafkaTestUtils.getSingleRecord(consumerServiceTest, TOPIC_NAME);
        RepertoireEvent resultRepertoireEvent = consumerRecordOfRepertoireEvent.value();
        assertEquals(resultRepertoireEvent.getTrack().getTrackName(), track.getTrackName());
        assertEquals(resultRepertoireEvent.getEventType(), repertoireEvent.getEventType());

        consumerServiceTest.close();
    }
}