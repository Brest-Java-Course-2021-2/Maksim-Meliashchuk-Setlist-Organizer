package com.epam.brest.web_app.controller;

import com.epam.brest.ApiException;
import com.epam.brest.model.Track;
import com.epam.brest.model.TrackDto;
import com.epam.brest.web_app.condition.ApiClientCondition;
import com.epam.brest.web_app.excel.RepertoireViewExportExcel;
import com.epam.brest.web_app.validator.TrackValidator;
import io.swagger.client.api.BandApi;
import io.swagger.client.api.TrackApi;
import io.swagger.client.api.TracksApi;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * MVC controller using ApiClient automatically generated by the Swagger Codegen.
 */
@Controller
@Conditional(ApiClientCondition.class)

public class TrackControllerApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackControllerApiClient.class);

    private final TrackValidator trackValidator;
    private final TracksApi tracksApi;
    private final TrackApi trackApi;
    private final BandApi bandApi;

    private List<TrackDto> trackDtoList;

    public TrackControllerApiClient(TrackValidator trackValidator, TracksApi tracksApi, TrackApi trackApi,
                                    BandApi bandApi) {
        this.trackValidator = trackValidator;
        this.tracksApi = tracksApi;
        this.trackApi = trackApi;
        this.bandApi = bandApi;
    }

    @GetMapping(value = "/track")
    public String gotoAddTrackPage(Model model) {
        LOGGER.debug("gotoAddTrackPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("track", new Track());
        try {
            model.addAttribute("bands", bandApi.bands());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        model.addAttribute("localDate", LocalDate.now());
        return "track";
    }

    @GetMapping(value = "/track/{id}")
    public final String gotoEditTrackPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("gotoEditTrackPage(id:{},model:{})", id, model);
        model.addAttribute("isNew", false);
        try {
            model.addAttribute("track", trackApi.getTrackById(id));
        } catch (ApiException e) {
            e.printStackTrace();
        }
        try {
            model.addAttribute("bands", bandApi.bands());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "track";
    }

    @PostMapping(value = "/track")
    public String addTrack(Track track, BindingResult result) {
        LOGGER.debug("addTrack({})", track);
        trackValidator.validate(track, result);
        if (result.hasErrors()) {
            return "track";
        }
        try {
            trackApi.createTrack(track);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "redirect:/repertoire";
    }

    @PostMapping(value = "/track/{id}")
    public String updateTrack(Track track, BindingResult result) {
        LOGGER.debug("updateTrack({})", track);
        trackValidator.validate(track, result);
        if (result.hasErrors()) {
            return "track";
        }
        try {
            trackApi.updateTrack(track);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "redirect:/repertoire";
    }

    @GetMapping(value = "/track/{id}/delete")
    public final String deleteTrackById(@PathVariable Integer id, Model model) {
        LOGGER.debug("delete({},{})", id, model);
        try {
            trackApi.deleteTrack(id);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "redirect:/repertoire";
    }

    @GetMapping(value = "/repertoire")
    public final String findAllTracksWithBandName(Model model) {
        LOGGER.debug("findAllTracksWithBandName()");
        try {
            trackDtoList = tracksApi.findAllTracksWithBandName();
            model.addAttribute("tracks", trackDtoList);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "repertoire";
    }

    @GetMapping(value = "/repertoire/filter/band/{id}")
    public final String gotoBandTracksPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("gotoBandTracksPage(id:{},model:{})", id, model);
        try {
            trackDtoList = tracksApi.findAllTracksWithBandNameByBandId(id);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        model.addAttribute("tracksDuration",
                Objects.requireNonNull(trackDtoList).stream().mapToInt(TrackDto::getTrackDuration).sum());
        model.addAttribute("tracks", trackDtoList);
        model.addAttribute("tracksCount", trackDtoList.size());
        return "bandtracks";
    }

    @GetMapping(value = "/repertoire/filter")
    public final String filterTrackByReleaseDate(@RequestParam(value = "fromDate", required = false)
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                 @RequestParam(value = "toDate", required = false)
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                                                 Model model) {
        LOGGER.debug("filterTrackByReleaseDate({},{})", fromDate, toDate);
        try {
            trackDtoList = tracksApi.findAllTracksWithReleaseDateFilter(fromDate, toDate);
            model.addAttribute("tracks", trackDtoList);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        return "repertoire";
    }

    @GetMapping(value = "/repertoire/fill")
    public String fillFakeTracks(@RequestParam(value = "size", required = false)
                                         Integer size,
                                 @RequestParam(value = "language", required = false)
                                         String language,
                                 Model model) {
        LOGGER.debug("fillFakeTracks({},{})", size, language);
        try {
            trackDtoList = tracksApi.fillTracksDtoFake(size, language);
            model.addAttribute("tracks", trackDtoList);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        model.addAttribute("size", size);
        model.addAttribute("language", language);
        return "repertoire";
    }

    @GetMapping(value = "/repertoire/export/excel")
    public ModelAndView exportToExcel() {
        LOGGER.debug("exportToExcel()");
        ModelAndView mav = new ModelAndView();
        mav.setView(new RepertoireViewExportExcel());
        mav.addObject("tracks", trackDtoList);
        return mav;
    }

    @GetMapping(value = "/track/export/excel")
    public void exportFromTrackTableToExcel(HttpServletResponse response) throws ApiException, IOException {
        LOGGER.debug("exportFromTrackTableToExcel()");
        InputStream is = new FileInputStream(trackApi.exportToExcelAllTracks());
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Track.xlsx");
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }
}
