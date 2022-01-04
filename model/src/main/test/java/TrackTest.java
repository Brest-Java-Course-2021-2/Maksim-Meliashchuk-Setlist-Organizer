import com.epam.brest.model.Track;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.epam.brest.model.constant.TrackConstant.TRACK_DETAILS_MAX_SIZE;
import static com.epam.brest.model.constant.TrackConstant.TRACK_NAME_MAX_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackTest {

    private static Validator validator;

    private final Logger logger = LogManager.getLogger(TrackTest.class);

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTrackNameIsNull() {
        logger.debug("testTrackNameIsNull()");
        Track track = new Track(null);

        Set<ConstraintViolation<Track>> constraintViolations = validator.validate(track);

        assertEquals(1, constraintViolations.size());
        assertEquals("Please provide track name!", constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testTrackNameIsEmpty() {
        logger.debug("testTrackNameIsEmpty()");
        Track track = new Track("");

        Set<ConstraintViolation<Track>> constraintViolations = validator.validate(track);

        assertEquals(1, constraintViolations.size());
        assertEquals("Please provide track name!", constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testTrackNameInvalidSize() {
        logger.debug("testTrackNameInvalidSize()");
        Track track = new Track(RandomStringUtils.randomAlphabetic(TRACK_NAME_MAX_SIZE + 1));

        Set<ConstraintViolation<Track>> constraintViolations = validator.validate(track);

        assertEquals(1, constraintViolations.size());
        assertEquals("Track name size have to be <= 100 symbols!",
                constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testTrackDetailsInvalidSize() {
        logger.debug("testTrackDetailsInvalidSize()");
        Track track = new Track(RandomStringUtils.randomAlphabetic(TRACK_NAME_MAX_SIZE));
        track.setTrackDetails( RandomStringUtils.randomAlphabetic(TRACK_DETAILS_MAX_SIZE + 1));

        Set<ConstraintViolation<Track>> constraintViolations = validator.validate(track);

        assertEquals(1, constraintViolations.size());
        assertEquals("Track details size have to be <= 2000 symbols!",
                constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testTrackBandIdIsInvalid() {
        logger.debug("testTrackBandIdIsInvalid()");
        Track track = new Track(RandomStringUtils.randomAlphabetic(TRACK_NAME_MAX_SIZE));
        track.setTrackBandId(-1);

        Set<ConstraintViolation<Track>> constraintViolations = validator.validate(track);

        assertEquals(1, constraintViolations.size());
        assertEquals("Band id should be positive",
                constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testTrackTempoIdIsInvalid() {
        logger.debug("testTrackTempoIdIsInvalid()");
        Track track = new Track(RandomStringUtils.randomAlphabetic(TRACK_NAME_MAX_SIZE));
        track.setTrackTempo(-1);

        Set<ConstraintViolation<Track>> constraintViolations = validator.validate(track);

        assertEquals(1, constraintViolations.size());
        assertEquals("Track tempo cannot be less than zero!",
                constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testTrackDurationIdIsInvalid() {
        logger.debug("testTrackTempoIdIsInvalid()");
        Track track = new Track(RandomStringUtils.randomAlphabetic(TRACK_NAME_MAX_SIZE));
        track.setTrackDuration(-1);

        Set<ConstraintViolation<Track>> constraintViolations = validator.validate(track);

        assertEquals(1, constraintViolations.size());
        assertEquals("Track duration cannot be less than zero!",
                constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testTrackLinkIdIsInvalid() {
        logger.debug("testTrackLinkIdIsInvalid()");
        Track track = new Track(RandomStringUtils.randomAlphabetic(TRACK_NAME_MAX_SIZE));
        track.setTrackLink("test link");

        Set<ConstraintViolation<Track>> constraintViolations = validator.validate(track);

        assertEquals(1, constraintViolations.size());
        assertEquals("Track link is not valid. The link must contain http or https!",
                constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testTrackIsValid() {
        logger.debug("testTrackIsValid()");
        Track track = new Track(RandomStringUtils.randomAlphabetic(TRACK_NAME_MAX_SIZE));
        track.setTrackDetails(RandomStringUtils.randomAlphabetic(TRACK_DETAILS_MAX_SIZE));
        track.setTrackDuration(1);
        track.setTrackTempo(1);
        track.setTrackBandId(1);
        track.setTrackLink("http://test.link");

        Set<ConstraintViolation<Track>> constraintViolations = validator.validate(track);

        assertEquals( 0, constraintViolations.size() );

    }
}
