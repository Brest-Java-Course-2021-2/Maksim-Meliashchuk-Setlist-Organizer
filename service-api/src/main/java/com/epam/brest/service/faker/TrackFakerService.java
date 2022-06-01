package com.epam.brest.service.faker;

import com.epam.brest.model.Track;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@PreAuthorize("hasAnyRole('user', 'admin')")
public interface TrackFakerService {

    List<Track> fillFakeTracks(Integer size, String language);

}
