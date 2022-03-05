package com.epam.brest.service.impl;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandFakerService;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BandFakerServiceImpl implements BandFakerService {

    final Integer DEFAULT_SIZE = 1;

    @Override
    public List<Band> fillFakeBands(Integer size, String language) {
        Locale locale = new Locale((language != null) ? language : "en");
        Faker faker = new Faker(locale);
        List<Band> bandList = null;
        if (size >= DEFAULT_SIZE) {
            bandList = IntStream.rangeClosed(DEFAULT_SIZE, size)
                    .mapToObj(i -> Band.builder()
                            .bandId(i)
                            .bandName(faker.rockBand().name() + i)
                            .bandDetails(faker.music().genre())
                            .build())
                    .collect(Collectors.toList());
        }
        return bandList;
    }
}
