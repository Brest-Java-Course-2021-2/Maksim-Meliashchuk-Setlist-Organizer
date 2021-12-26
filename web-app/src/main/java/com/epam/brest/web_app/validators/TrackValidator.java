package com.epam.brest.web_app.validators;


import com.epam.brest.model.Track;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.epam.brest.model.constant.TrackConstant.*;

@Component
public class TrackValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Track.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);

        ValidationUtils.rejectIfEmpty(errors, "trackName", "trackName.empty");
        Track track = (Track) target;

        if (StringUtils.hasLength(track.getTrackName())
                && track.getTrackName().length() > TRACK_NAME_MAX_SIZE) {
            errors.rejectValue("trackName", "trackName.maxSize");
        }
        if (StringUtils.hasLength(track.getTrackDetails())
                && track.getTrackDetails().length() > TRACK_DETAILS_MAX_SIZE) {
            errors.rejectValue("trackDetails", "trackDetails.maxSize");
        }

        if (StringUtils.hasLength(track.getTrackLink())
                && track.getTrackLink().length() > TRACK_LINK_MAX_SIZE) {
            errors.rejectValue("trackLink", "trackLink.maxSize");
        }

        if (StringUtils.hasLength(track.getTrackLink())
                && !urlValidator.isValid(track.getTrackLink())) {
            errors.rejectValue("trackLink", "trackLink.notValid");
        }



    }


}
