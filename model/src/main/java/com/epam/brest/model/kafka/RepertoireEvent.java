package com.epam.brest.model.kafka;

import com.epam.brest.model.TrackDto;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepertoireEvent {

    private EventType eventType;
    private TrackDto track;
}
