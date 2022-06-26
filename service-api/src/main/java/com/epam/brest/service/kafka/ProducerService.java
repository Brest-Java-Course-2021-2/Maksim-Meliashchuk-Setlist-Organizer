package com.epam.brest.service.kafka;


import com.epam.brest.model.kafka.RepertoireEvent;

public interface ProducerService {

    void sendRepertoireMessage(String topic, RepertoireEvent repertoireEvent);
}
