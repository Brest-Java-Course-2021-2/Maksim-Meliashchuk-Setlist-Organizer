package com.epam.brest.service;

import com.epam.brest.model.Band;

public interface BandService {

    Integer create(Band band);

    Integer count();

}
