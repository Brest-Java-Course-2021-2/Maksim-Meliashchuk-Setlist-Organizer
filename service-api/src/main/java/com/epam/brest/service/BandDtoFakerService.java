package com.epam.brest.service;

import com.epam.brest.model.BandDto;

import java.util.List;

public interface BandDtoFakerService {

    List<BandDto> fillFakeBandsDto(Integer size, String language);

}
