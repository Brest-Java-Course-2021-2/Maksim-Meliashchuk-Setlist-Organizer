package com.epam.brest.web_app.controller;

import com.epam.brest.model.Track;
import com.epam.brest.model.TrackDto;
import com.epam.brest.service.BandService;
import com.epam.brest.service.TrackDtoFakerService;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.TrackService;
import com.epam.brest.web_app.validator.TrackValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

/**
 * MVC controller.
 */
@Controller
@ConditionalOnExpression(
        "'${app.httpClient}'=='RestTemplate' or '${app.httpClient}'=='WebClient'"
)
public class TrackController {

    private final TrackService trackService;

    private final BandService bandService;

    private final TrackDtoService trackDtoService;

    private final TrackDtoFakerService trackDtoFakerService;

    private final TrackValidator trackValidator;

    private final Logger logger = LogManager.getLogger(TrackController.class);

    public TrackController(TrackService trackService, BandService bandService, TrackDtoService trackDtoService,
                           TrackDtoFakerService trackDtoFakerService, TrackValidator trackValidator) {
        this.trackService = trackService;
        this.bandService = bandService;
        this.trackDtoService = trackDtoService;
        this.trackDtoFakerService = trackDtoFakerService;
        this.trackValidator = trackValidator;
    }

    @GetMapping(value = "/track")
    public String gotoAddTrackPage(Model model) {
        logger.debug("gotoAddTrackPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("track", new Track());
        model.addAttribute("bands", bandService.findAllBands());
        model.addAttribute("localDate", LocalDate.now());
        return "track";
    }

    @GetMapping(value = "/track/{id}")
    public final String gotoEditTrackPage(@PathVariable Integer id, Model model) {
        logger.debug("gotoEditTrackPage(id:{},model:{})", id, model);
        model.addAttribute("isNew", false);
        model.addAttribute("track", trackService.getTrackById(id));
        model.addAttribute("bands", bandService.findAllBands());
        return "track";
    }

    @PostMapping(value = "/track")
    public String addTrack(Track track, BindingResult result) {
        logger.debug("addTrack({})", track);
        trackValidator.validate(track, result);
        if (result.hasErrors()) {
            return "track";
        }
        this.trackService.create(track);
        return "redirect:/repertoire";
    }

    @PostMapping(value = "/track/{id}")
    public String updateTrack(Track track, BindingResult result) {
        logger.debug("updateTrack({})", track);
        trackValidator.validate(track, result);
        if (result.hasErrors()) {
            return "track";
        }
        this.trackService.update(track);
        return "redirect:/repertoire";
    }

    @GetMapping(value = "/track/{id}/delete")
    public final String deleteTrackById(@PathVariable Integer id, Model model) {
        logger.debug("delete({},{})", id, model);
        trackService.delete(id);
        return "redirect:/repertoire";
    }

    @GetMapping(value = "/repertoire")
    public final String findAllTracksWithBandName(Model model) {
        logger.debug("findAllTracksWithBandName()");
        model.addAttribute("tracks", trackDtoService.findAllTracksWithBandName());
        return "repertoire";
    }

    @GetMapping(value = "/repertoire/fill")
    public final String fillFakerTracks(@RequestParam(value = "size", required = false)
                                                    Integer size,
                                        @RequestParam(value = "language", required = false)
                                                    String language,
                                        Model model) {
        logger.debug("fillFakerTracks()");
        model.addAttribute("tracks", trackDtoFakerService.fillFakeTracksDto(size, language));
        return "repertoire";
    }

    @GetMapping(value = "/repertoire/filter/band/{id}")
    public final String gotoBandTracksPage(@PathVariable Integer id, Model model) {
        logger.debug("gotoBandTracksPage(id:{},model:{})", id, model);
        List<TrackDto> trackDtoList = trackDtoService.findAllTracksWithBandNameByBandId(id);
        model.addAttribute("tracksDuration",
                trackDtoList.stream().mapToInt(TrackDto::getTrackDuration).sum());
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
        logger.debug("filterTrackByReleaseDate({},{})", fromDate, toDate);
        model.addAttribute("tracks", trackDtoService.findAllTracksWithReleaseDateFilter(fromDate, toDate));
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        return "repertoire";
    }

}
