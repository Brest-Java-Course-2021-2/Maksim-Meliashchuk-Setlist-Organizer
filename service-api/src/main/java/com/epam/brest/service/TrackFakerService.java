package com.epam.brest.service;

import com.epam.brest.model.Track;

import java.util.List;
//TODO TrackFakerService impl
public interface TrackFakerService {

    List<Track> fillFakeTracks(Integer size, String language);

}
