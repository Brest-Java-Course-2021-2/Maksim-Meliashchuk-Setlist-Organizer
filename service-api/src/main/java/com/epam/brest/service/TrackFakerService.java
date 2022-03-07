package com.epam.brest.service;

import com.epam.brest.model.Track;

import java.util.List;

public interface TrackFakerService {

    List<Track> fillFakeTracks(Integer size, String language);

}
