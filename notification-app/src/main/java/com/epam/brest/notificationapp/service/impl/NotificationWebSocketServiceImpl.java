package com.epam.brest.notificationapp.service.impl;

import com.epam.brest.model.kafka.RepertoireEvent;
import com.epam.brest.notificationapp.service.api.NotificationWebSocketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Objects;

@Service
public class NotificationWebSocketServiceImpl implements NotificationWebSocketService {

    @Value("${websocket.message.track.created}")
    private String messageTrackCreated;
    @Value("${websocket.message.track.updated}")
    private String messageTrackUpdated;
    @Value("${websocket.message.track.deleted}")
    private String messageTrackDeleted;

    @Override
    public String prepareRepertoireMessage(RepertoireEvent event) {

        switch (event.getEventType()) {
            case CREATE_TRACK:
                return MessageFormat.format(Objects.requireNonNull(messageTrackCreated),
                        event.getTrack().getTrackName(), event.getTrack().getTrackBandName()) ;
            case UPDATE_TRACK:
                return MessageFormat.format(Objects.requireNonNull(messageTrackUpdated),
                        event.getTrack().getTrackName(), event.getTrack().getTrackBandName()) ;
            case DELETE_TRACK:
                return MessageFormat.format(Objects.requireNonNull(messageTrackDeleted),
                        event.getTrack().getTrackName(), event.getTrack().getTrackBandName()) ;
        }
        return "unknown message";
    }
}
