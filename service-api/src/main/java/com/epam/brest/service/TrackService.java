package com.epam.brest.service;

import com.epam.brest.model.Track;

public interface TrackService {

    Track getTrackById(Integer trackId);

    Integer create(Track track);

    Integer update(Track track);

    Integer delete(Integer trackId);

    Integer count();

}
