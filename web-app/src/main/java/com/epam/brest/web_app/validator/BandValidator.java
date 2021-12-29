package com.epam.brest.web_app.validator;


import com.epam.brest.model.Band;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.epam.brest.model.constant.BandConstant.BAND_DETAILS_MAX_SIZE;
import static com.epam.brest.model.constant.BandConstant.BAND_NAME_MAX_SIZE;

@Component
public class BandValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Band.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "bandName", "bandName.empty");
        Band band = (Band) target;

        if (StringUtils.hasLength(band.getBandName())
                && band.getBandName().length() > BAND_NAME_MAX_SIZE) {
            errors.rejectValue("bandName", "bandName.maxSize");
        }
        if (StringUtils.hasLength(band.getBandDetails())
                && band.getBandDetails().length() > BAND_DETAILS_MAX_SIZE) {
            errors.rejectValue("bandDetails", "bandDetails.maxSize");
        }
    }

}
