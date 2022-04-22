package com.epam.brest.delegateimpl;

import com.epam.brest.api.RepertoireApiDelegate;
import com.epam.brest.model.Track;
import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.excel.TrackExportExcelService;
import com.epam.brest.service.excel.TrackImportExcelService;
import com.epam.brest.service.faker.TrackFakerService;
import com.epam.brest.service.xml.TrackExportXmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class RepertoireDelegateImpl implements RepertoireApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepertoireDelegateImpl.class);
    private final TrackService trackService;
    private final TrackDtoService trackDtoService;
    private final TrackFakerService trackFakerService;
    private final TrackExportExcelService trackExportExcelService;
    private final TrackImportExcelService trackImportExcelService;
    private final TrackExportXmlService trackExportXmlService;

    public RepertoireDelegateImpl(TrackService trackService, TrackDtoService trackDtoService,
                                  TrackFakerService trackFakerService, TrackExportExcelService trackExportExcelService,
                                  TrackImportExcelService trackImportExcelService, TrackExportXmlService trackExportXmlService) {
        this.trackService = trackService;
        this.trackDtoService = trackDtoService;
        this.trackFakerService = trackFakerService;
        this.trackExportExcelService = trackExportExcelService;
        this.trackImportExcelService = trackImportExcelService;
        this.trackExportXmlService = trackExportXmlService;
    }

    @Override
    public ResponseEntity<List<Track>> tracks() {
        LOGGER.debug("tracks()");
        List<Track> resultList = trackService.findAllTracks();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Track>> tracksFake(Integer size, String language) {
        LOGGER.debug("tracksFake()");
        List<Track> resultList = trackFakerService.fillFakeTracks(size, language);
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

    @Override
    public ResponseEntity<Resource> exportToExcelAllTracks() {
        LOGGER.debug("exportToExcelAllBandsDto()");
        HttpHeaders headers = new HttpHeaders();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        if (response != null) {
            response.setHeader("Content-Disposition", "attachment; filename=Tracks.xlsx");
        }
        trackExportExcelService.exportTracksExcel(response);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> importTrackFromExcel(MultipartFile file) {
        LOGGER.debug("importTrackFromExcel({})", file.getName());
        int result = 0;
        try {
            result =  trackImportExcelService.importTrackExcel(file).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource> exportToXmlAlTracks() {
        LOGGER.debug("exportToXmlAllTracks()");
        HttpHeaders headers = new HttpHeaders();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
        headers.setContentType(MediaType.parseMediaType("application/xml"));
        if (response != null) {
            response.setHeader("Content-Disposition", "attachment; filename=Tracks.xml");
        }
        try {
            trackExportXmlService.exportTracksXml(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
