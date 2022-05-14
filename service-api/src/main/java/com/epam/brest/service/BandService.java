package com.epam.brest.service;

import com.epam.brest.model.Band;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface BandService {
    @PreAuthorize("hasAnyRole('user', 'admin')")
    Band getBandById(Integer bandId);

    @PreAuthorize("hasAnyRole('user', 'admin')")
    List<Band> findAllBands();

    @PreAuthorize("hasAnyRole('admin')")
    Integer create(Band band);

    @PreAuthorize("hasAnyRole('admin')")
    Integer update(Band band);

    @PreAuthorize("hasAnyRole('admin')")
    Integer delete(Integer bandId);

    @PreAuthorize("hasAnyRole('user', 'admin')")
    Integer count();

    @PreAuthorize("hasAnyRole('admin')")
    void deleteAllBands();

}
