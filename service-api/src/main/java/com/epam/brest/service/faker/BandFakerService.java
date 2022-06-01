package com.epam.brest.service.faker;

import com.epam.brest.model.Band;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@PreAuthorize("hasAnyRole('user', 'admin')")
public interface BandFakerService {

    List<Band> fillFakeBands(Integer size, String language);

}
