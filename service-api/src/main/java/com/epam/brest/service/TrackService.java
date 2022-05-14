package com.epam.brest.service;

import com.epam.brest.model.Track;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface TrackService {

    @PreAuthorize("hasAnyRole('user', 'admin')")
    Track getTrackById(Integer trackId);

    @PreAuthorize("hasAnyRole('admin')")
    Integer create(Track track);

    @PreAuthorize("hasAnyRole('admin')")
    Integer update(Track track);

    @PreAuthorize("hasAnyRole('admin')")
    Integer delete(Integer trackId);

    @PreAuthorize("hasAnyRole('user', 'admin')")
    Integer count();

    @PreAuthorize("hasAnyRole('user', 'admin')")
    List<Track> findAllTracks();

    @PreAuthorize("hasAnyRole('admin')")
    void deleteAllTracks();


}
