package com.epam.brest.service.faker;

import com.epam.brest.model.BandDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@PreAuthorize("hasAnyRole('user', 'admin')")
public interface BandDtoFakerService {

    List<BandDto> fillFakeBandsDto(Integer size, String language);

}
