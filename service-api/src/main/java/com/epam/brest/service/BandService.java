package com.epam.brest.service;

import com.epam.brest.model.Band;

import java.util.List;

public interface BandService {

    Band getBandById(Integer bandId);

    List<Band> findAllBands();

    Integer create(Band band);

    Integer update(Band band);

    Integer delete(Integer bandId);

    Integer count();

    void deleteAllBands();

}
