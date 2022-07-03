package com.epam.brest.notificationapp.service.api;


import com.epam.brest.model.kafka.RepertoireEvent;

/**
 * NotificationWebSocketService Interface.
 */
public interface NotificationWebSocketService {
    /**
     * Prepares a message from a message broker to be sending to a WebSocket.
     *
     * @return String prepared message.
     */
    String prepareRepertoireMessage(RepertoireEvent event);
}
