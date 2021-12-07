package com.epam.brest.web_app.validator;


import com.epam.brest.model.Track;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.epam.brest.model.constant.TrackConstant.TRACK_DETAILS_MAX_SIZE;
import static com.epam.brest.model.constant.TrackConstant.TRACK_NAME_MAX_SIZE;
import static com.epam.brest.model.constant.TrackConstant.TRACK_LINK_MAX_SIZE;

@Component
public class TrackValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Track.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

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
    }


}
