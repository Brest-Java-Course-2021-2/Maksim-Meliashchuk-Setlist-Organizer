package com.epam.brest.service.faker;

import com.epam.brest.model.TrackDto;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TrackDtoFakerServiceImpl implements TrackDtoFakerService {

    private final Logger logger = LogManager.getLogger(TrackDtoFakerServiceImpl.class);

    final int DEFAULT_SIZE = 1;
    final int HIGH_BAND_COUNT = 100;
    final int HIGH_TRACK_TEMPO = 240;
    final int LOW_TRACK_TEMPO = 40;
    final int HIGH_TRACK_DURATION = 420000;
    final int LOW_TRACK_DURATION = 200000;

    @Override
    public List<TrackDto> fillFakeTracksDto(Integer size, String language) {
        logger.debug("fillFakeTracksDto({size},{language})", size, language);
        Faker faker = new Faker(new Locale(language));
        List<TrackDto> trackDtoList = null;
        Random random = new Random();
        if (size >= DEFAULT_SIZE) {
            trackDtoList = IntStream.rangeClosed(DEFAULT_SIZE, size)
                    .mapToObj(i -> TrackDto.builder()
                            .trackId(i)
                            .trackBandId(random.nextInt(HIGH_BAND_COUNT + 1))
                            .trackName(faker.book().title())
                            .trackBandName(faker.rockBand().name().toUpperCase())
                            .trackDetails(faker.music().genre() + " " +
                                    faker.music().instrument() + " " + faker.music().key() +
                                    " " + faker.music().chord())
                            .trackLink("https://youtube.com/" + faker.regexify("[a-z1-9]{10}"))
                            .trackTempo(random.nextInt(HIGH_TRACK_TEMPO - LOW_TRACK_TEMPO) +
                                    LOW_TRACK_TEMPO)
                            .trackDuration(random.nextInt(HIGH_TRACK_DURATION - LOW_TRACK_DURATION) +
                                    LOW_TRACK_DURATION)
                            .trackReleaseDate(LocalDate.of((random.nextInt(32) + 1990),
                                    random.nextInt(12) + 1, random.nextInt(25) + 1 ))
                            .build())
                    .collect(Collectors.toList());
        }
        return trackDtoList;
    }
}
