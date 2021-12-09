package com.epam.brest.service.exception;

import org.springframework.dao.EmptyResultDataAccessException;

public class BandNotFoundException extends EmptyResultDataAccessException {
    public BandNotFoundException(Integer id) {
        super("Band not found for id: " + id, 1);
    }
}