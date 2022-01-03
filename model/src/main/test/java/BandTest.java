import com.epam.brest.model.Band;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BandTest {

    private static Validator validator;

    private final Logger logger = LogManager.getLogger(BandTest.class);

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) factory.getValidator();
    }

    @Test
    public void testBandNameIsNull() {
        logger.debug("testBandNameIsNull()");
        Band band = new Band(null);

        Set<ConstraintViolation<Band>> constraintViolations = validator.validate(band);

        assertEquals( 1, constraintViolations.size());
        assertEquals( "Please provide band name!", constraintViolations.iterator().next().getMessage());

    }
}
