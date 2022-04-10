package com.epam.brest.delegateimpl;

import com.epam.brest.api.VersionApiDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VersionDelegateImpl implements VersionApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TracksDtoDelegateImpl.class);

    private final static String VERSION = "1.0.0";

    @Override
    public ResponseEntity<String> version() {
        LOGGER.debug("version()");
        return new ResponseEntity<>(VERSION, HttpStatus.OK);
    }

}
