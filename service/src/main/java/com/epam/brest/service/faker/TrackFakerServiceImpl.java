package com.epam.brest.service.faker;

import com.epam.brest.model.Track;
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
public class TrackFakerServiceImpl implements TrackFakerService {

    private final Logger logger = LogManager.getLogger(TrackFakerServiceImpl.class);

    final int DEFAULT_SIZE = 1;
    final int HIGH_BAND_COUNT = 100;
    final int HIGH_TRACK_TEMPO = 240;
    final int LOW_TRACK_TEMPO = 40;
    final int HIGH_TRACK_DURATION = 420000;
    final int LOW_TRACK_DURATION = 200000;

    @Override
    public List<Track> fillFakeTracks(Integer size, String language) {
        logger.debug("fillFakeTracks({size},{language})", size, language);
        Faker faker = new Faker(new Locale(language));
        List<Track> trackList = null;
        Random random = new Random();
        if (size >= DEFAULT_SIZE) {
            trackList = IntStream.rangeClosed(DEFAULT_SIZE, size)
                    .mapToObj(i -> Track.builder()
                            .trackId(i)
                            .trackBandId(random.nextInt(HIGH_BAND_COUNT + 1))
                            .trackName(faker.book().title())
                            .trackDetails(faker.music().genre() + " " +
                                    faker.music().instrument() + " " + faker.music().key() +
                                    " " + faker.music().chord() + " " + faker.book().publisher())
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
        return trackList;
    }
}
