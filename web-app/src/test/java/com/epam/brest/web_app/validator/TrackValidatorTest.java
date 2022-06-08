package com.epam.brest.web_app.validator;

import com.epam.brest.model.Track;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.epam.brest.model.constant.TrackConstant.*;

class TrackValidatorTest {

    private static Validator testValidator;

    private static Errors errors;

    private static Track track;

    private final Logger logger = LogManager.getLogger(TrackValidatorTest.class);

    @BeforeAll
    public static void setUpValidator() {
        track = new Track();
        testValidator = new TrackValidator();
        errors = new BeanPropertyBindingResult(track, "track");
    }

    @Test
    void testTrackValidate() {
        logger.debug("testTrackValidate()");

        testValidator.validate(track, errors);
        Assertions.assertTrue(errors.hasFieldErrors("trackName"));

        track.setTrackName(RandomStringUtils.randomAlphabetic(TRACK_NAME_MAX_SIZE + 1));
        testValidator.validate(track, errors);
        Assertions.assertTrue(errors.hasFieldErrors("trackName"));

        track.setTrackDetails(RandomStringUtils.randomAlphabetic(TRACK_DETAILS_MAX_SIZE + 1));
        testValidator.validate(track, errors);
        Assertions.assertTrue(errors.hasFieldErrors("trackDetails"));

        track.setTrackLink(RandomStringUtils.randomAlphabetic(TRACK_LINK_MAX_SIZE + 1));
        testValidator.validate(track, errors);
        Assertions.assertTrue(errors.hasFieldErrors("trackLink"));

        track.setTrackTempo(-1);
        testValidator.validate(track, errors);
        Assertions.assertTrue(errors.hasFieldErrors("trackTempo"));
    }

    @Test
    void testTrackLinkValidate() {
        logger.debug("testTrackLinkValidate()");

        track.setTrackLink("http://testlink.com");
        testValidator.validate(track, errors);
        Assertions.assertFalse(errors.hasFieldErrors("trackLink"));

        track.setTrackLink("testlink");
        testValidator.validate(track, errors);
        Assertions.assertTrue(errors.hasFieldErrors("trackLink"));

    }


}