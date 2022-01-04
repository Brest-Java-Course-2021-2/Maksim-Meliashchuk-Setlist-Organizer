package com.epam.brest.service.exception;

import org.springframework.dao.EmptyResultDataAccessException;

public class TrackNotFoundException extends EmptyResultDataAccessException {
    public TrackNotFoundException(Integer id) {
        super("Track not found for id: " + id, 1);
    }
}
