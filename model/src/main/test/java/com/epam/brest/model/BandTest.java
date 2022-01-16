package com.epam.brest.model;

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

import static com.epam.brest.model.constant.BandConstant.BAND_DETAILS_MAX_SIZE;
import static com.epam.brest.model.constant.BandConstant.BAND_NAME_MAX_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BandTest {

    private static Validator validator;

    private final Logger logger = LogManager.getLogger(BandTest.class);

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testBandNameIsNull() {
        logger.debug("testBandNameIsNull()");
        Band band = new Band(null);

        Set<ConstraintViolation<Band>> constraintViolations = validator.validate(band);

        assertEquals( 1, constraintViolations.size());
        assertEquals( "Please provide band name!", constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testBandNameIsEmpty() {
        logger.debug("testBandNameIsEmpty()");
        Band band = new Band("");

        Set<ConstraintViolation<Band>> constraintViolations = validator.validate(band);

        assertEquals( 1, constraintViolations.size());
        assertEquals( "Please provide band name!", constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testBandNameInvalidSize() {
        logger.debug("testBandNameInvalidSize()");
        Band band = new Band(RandomStringUtils.randomAlphabetic(BAND_NAME_MAX_SIZE + 1));

        Set<ConstraintViolation<Band>> constraintViolations = validator.validate(band);

        assertEquals( 1, constraintViolations.size());
        assertEquals( "Band name size have to be <= 100 symbols!",
                constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testBandDetailsInvalidSize() {
        logger.debug("testBandDetailsInvalidSize()");
        Band band = new Band(RandomStringUtils.randomAlphabetic(BAND_NAME_MAX_SIZE),
                RandomStringUtils.randomAlphabetic(BAND_DETAILS_MAX_SIZE + 1));

        Set<ConstraintViolation<Band>> constraintViolations = validator.validate(band);

        assertEquals( 1, constraintViolations.size());
        assertEquals( "Band details size have to be <= 1000 symbols!",
                constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void testBandDetailsIsValid() {
        logger.debug("testBandDetailsIsValid()");

        Band band = new Band(RandomStringUtils.randomAlphabetic(BAND_NAME_MAX_SIZE),
                RandomStringUtils.randomAlphabetic(BAND_DETAILS_MAX_SIZE));

        Set<ConstraintViolation<Band>> constraintViolations =
                validator.validate(band);

        assertEquals( 0, constraintViolations.size() );
    }
}
