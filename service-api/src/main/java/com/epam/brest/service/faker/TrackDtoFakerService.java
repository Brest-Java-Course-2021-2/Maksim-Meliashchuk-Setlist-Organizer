package com.epam.brest.service.faker;

import com.epam.brest.model.TrackDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@PreAuthorize("hasAnyRole('user', 'admin')")
public interface TrackDtoFakerService {

    List<TrackDto> fillFakeTracksDto(Integer size, String language);

}
