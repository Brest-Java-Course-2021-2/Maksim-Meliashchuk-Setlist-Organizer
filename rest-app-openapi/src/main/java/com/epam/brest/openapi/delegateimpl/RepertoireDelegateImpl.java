package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.model.Track;
import com.epam.brest.model.TrackDto;
import com.epam.brest.openapi.api.RepertoireApiDelegate;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.TrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RepertoireDelegateImpl implements RepertoireApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepertoireDelegateImpl.class);

    private final TrackService trackService;

    private final TrackDtoService trackDtoService;

    public RepertoireDelegateImpl(TrackService trackService, TrackDtoService trackDtoService) {
        this.trackService = trackService;
        this.trackDtoService = trackDtoService;
    }

    @Override
    public ResponseEntity<List<Track>> tracks() {
        LOGGER.debug("tracks()");
        List<Track> resultList = trackService.findAllTracks();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> createTrack(Track track) {
        LOGGER.debug("createTrack({})", track);
        Integer id = trackService.create(track);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> deleteTrack(Integer id) {
        LOGGER.debug("delete({})", id);
        int result =  trackService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TrackDto>> findAllTracksWithBandNameByBandId(Integer bandId) {
        LOGGER.debug("findAllTracksWithBandNameByBandId()");
        List<TrackDto> resultList = trackDtoService.findAllTracksWithBandNameByBandId(bandId);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TrackDto>> findAllTracksWithReleaseDateFilter(LocalDate fromDate, LocalDate toDate) {
        LOGGER.debug("findAllTracksWithReleaseDateFilter()");
        List<TrackDto> resultList = trackDtoService.findAllTracksWithReleaseDateFilter(fromDate, toDate);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Track> getTrackById(Integer id) {
        LOGGER.debug("getTrackById()");
        Track result = trackService.getTrackById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> updateTrack(Track track) {
        LOGGER.debug("updateTrack({})", track);
        int result = trackService.update(track);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
