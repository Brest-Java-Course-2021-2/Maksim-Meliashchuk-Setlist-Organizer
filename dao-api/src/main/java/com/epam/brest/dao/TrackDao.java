package com.epam.brest.dao;

import com.epam.brest.model.Track;

import java.util.List;

public interface TrackDao {

    List<Track> findAll();

    Track getTrackById(Integer trackId);

    Integer create(Track track);

    Integer update(Track track);

    Integer delete(Integer trackId);

    Integer count();
}
