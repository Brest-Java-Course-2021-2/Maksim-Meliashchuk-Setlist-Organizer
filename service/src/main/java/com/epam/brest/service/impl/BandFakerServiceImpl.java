package com.epam.brest.service.impl;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandFakerService;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BandFakerServiceImpl implements BandFakerService {

    private final Logger logger = LogManager.getLogger(BandFakerServiceImpl.class);

    final int DEFAULT_SIZE = 1;

    @Override
    public List<Band> fillFakeBands(Integer size, String language) {
        logger.debug("fillFakeBandsDto({size},{language})", size, language);
        Faker faker = new Faker(new Locale(language));
        List<Band> bandList = null;
        if (size >= DEFAULT_SIZE) {
            bandList = IntStream.rangeClosed(DEFAULT_SIZE, size)
                    .mapToObj(i -> Band.builder()
                            .bandId(i)
                            .bandName(faker.rockBand().name() + i)
                            .bandDetails(faker.music().genre() + " " + faker.book().title())
                            .build())
                    .collect(Collectors.toList());
        }
        return bandList;
    }
}
