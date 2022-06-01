package com.epam.brest.service.exception;

import org.springframework.dao.EmptyResultDataAccessException;

public class BandNotUniqueException  extends EmptyResultDataAccessException {
    public BandNotUniqueException(String name) {
        super("Band not unique: " + name, 1);
    }
}
