package com.epam.brest.service;

import com.epam.brest.model.Band;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface BandService {

    Band getBandById(Integer bandId);

    @PreAuthorize("hasAnyRole('user', 'admin')")
    List<Band> findAllBands();

    Integer create(Band band);

    Integer update(Band band);

    Integer delete(Integer bandId);

    Integer count();

    void deleteAllBands();

}
