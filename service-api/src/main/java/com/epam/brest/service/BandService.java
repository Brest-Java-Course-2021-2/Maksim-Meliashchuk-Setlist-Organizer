package com.epam.brest.service;

import com.epam.brest.model.Band;

public interface BandService {

    Band getBandById(Integer bandId);

    Integer create(Band band);

    Integer update(Band band);

    Integer delete(Integer bandId);

    Integer count();

}
