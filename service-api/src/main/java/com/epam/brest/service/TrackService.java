package com.epam.brest.service;

import com.epam.brest.model.Track;

import java.util.List;

public interface TrackService {

    Track getTrackById(Integer trackId);

    Integer create(Track track);

    Integer update(Track track);

    Integer delete(Integer trackId);

    Integer count();

    List<Track> findAllTracks();

}
