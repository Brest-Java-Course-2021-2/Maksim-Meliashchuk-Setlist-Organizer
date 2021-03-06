package com.epam.brest.service.faker;

import com.epam.brest.model.BandDto;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BandDtoFakerServiceImpl implements BandDtoFakerService {

    private final Logger logger = LogManager.getLogger(BandDtoFakerServiceImpl.class);

    final int DEFAULT_SIZE = 1;
    final int LOW_TRACK_DURATION = 200000;
    final int HIGH_TRACK_DURATION = 420000;
    final int HIGH_TRACK_COUNT = 200;

    @Override
    public List<BandDto> fillFakeBandsDto(Integer size, String language) {
        logger.debug("fillFakeBandsDto({size},{language})", size, language);
        Faker faker = new Faker(new Locale(language));
        List<BandDto> bandDtoList = null;
        Random random = new Random();
        if (size >= DEFAULT_SIZE) {
            bandDtoList = IntStream.rangeClosed(DEFAULT_SIZE, size)
                    .mapToObj(i -> BandDto.builder()
                            .bandId(i)
                            .bandName(faker.rockBand().name().toUpperCase())
                            .bandDetails(faker.music().genre() + " " + faker.book().title())
                            .bandCountTrack(random.nextInt(HIGH_TRACK_COUNT))
                            .bandRepertoireDuration(random.nextInt(HIGH_TRACK_COUNT) *
                                    random.nextInt(HIGH_TRACK_DURATION - LOW_TRACK_DURATION) +
                                    LOW_TRACK_DURATION)
                            .build())
                    .collect(Collectors.toList());
        }
        return bandDtoList;
    }
}
