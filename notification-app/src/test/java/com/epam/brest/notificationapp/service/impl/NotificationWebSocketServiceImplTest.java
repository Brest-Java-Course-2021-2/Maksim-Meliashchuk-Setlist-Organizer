package com.epam.brest.notificationapp.service.impl;

import com.epam.brest.model.TrackDto;
import com.epam.brest.model.kafka.EventType;
import com.epam.brest.model.kafka.RepertoireEvent;
import com.epam.brest.notificationapp.service.api.NotificationWebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(MockitoExtension.class)
class NotificationWebSocketServiceImplTest {
    @InjectMocks
    private final NotificationWebSocketService notificationWebSocketService = new NotificationWebSocketServiceImpl();

    @Test
    void prepareRepertoireMessageTest() {
        log.debug("prepareRepertoireMessageTest");

        final String MESSAGE_TRACK_CREATED = "The track {0} of the {1} band has been created";
        final String MESSAGE_TRACK_UPDATED = "The track {0} of the {1} band has been updated";
        final String MESSAGE_TRACK_DELETED = "The track {0} of the {1} band has been deleted";

        var track = TrackDto.builder()
                .trackName("Smoke on the Water")
                .trackBandName("Deep Purple")
                .build();
        var createEvent = RepertoireEvent.builder()
                .eventType(EventType.CREATE_TRACK)
                .track(track)
                .build();
        var updateEvent = RepertoireEvent.builder()
                .eventType(EventType.UPDATE_TRACK)
                .track(track)
                .build();
        var deleteEvent = RepertoireEvent.builder()
                .eventType(EventType.DELETE_TRACK)
                .track(track)
                .build();

        ReflectionTestUtils.setField(notificationWebSocketService, "messageTrackCreated", MESSAGE_TRACK_CREATED);
        ReflectionTestUtils.setField(notificationWebSocketService, "messageTrackUpdated", MESSAGE_TRACK_UPDATED);
        ReflectionTestUtils.setField(notificationWebSocketService, "messageTrackDeleted", MESSAGE_TRACK_DELETED);

        var preparedMessageCreate = notificationWebSocketService.prepareRepertoireMessage(createEvent);
        var preparedMessageUpdate = notificationWebSocketService.prepareRepertoireMessage(updateEvent);
        var preparedMessageDelete = notificationWebSocketService.prepareRepertoireMessage(deleteEvent);

        var expectedMessageCreate = "The track Smoke on the Water of the Deep Purple band has been created";
        var expectedMessageUpdate = "The track Smoke on the Water of the Deep Purple band has been updated";
        var expectedMessageDelete = "The track Smoke on the Water of the Deep Purple band has been deleted";

        assertEquals(preparedMessageCreate, expectedMessageCreate);
        assertEquals(preparedMessageUpdate, expectedMessageUpdate);
        assertEquals(preparedMessageDelete, expectedMessageDelete);
    }
}