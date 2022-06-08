package com.epam.brest.web_app.validator;

import com.epam.brest.model.Band;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.epam.brest.model.constant.BandConstant.BAND_DETAILS_MAX_SIZE;
import static com.epam.brest.model.constant.BandConstant.BAND_NAME_MAX_SIZE;

class BandValidatorTest {

    private static Validator testValidator;

    private static Errors errors;

    private static Band band;

    private final Logger logger = LogManager.getLogger(BandValidatorTest.class);

    @BeforeAll
    public static void setUpValidator() {
        band = new Band();
        testValidator = new BandValidator();
        errors = new BeanPropertyBindingResult(band, "band");
    }

    @Test
    void testBandValidate() {
        logger.debug("testBandValidate()");

        testValidator.validate(band, errors);
        Assertions.assertTrue(errors.hasFieldErrors("bandName"));

        band.setBandName(RandomStringUtils.randomAlphabetic(BAND_NAME_MAX_SIZE + 1));
        testValidator.validate(band, errors);
        Assertions.assertTrue(errors.hasFieldErrors("bandName"));

        band.setBandDetails(RandomStringUtils.randomAlphabetic(BAND_DETAILS_MAX_SIZE + 1));
        testValidator.validate(band, errors);
        Assertions.assertTrue(errors.hasFieldErrors("bandDetails"));


    }
}