package com.epam.brest.service.faker;

import com.epam.brest.model.Band;

import java.util.List;

public interface BandFakerService {

    List<Band> fillFakeBands(Integer size, String language);

}
