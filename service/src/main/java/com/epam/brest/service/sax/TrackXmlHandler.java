package com.epam.brest.service.sax;

import com.epam.brest.model.Track;
import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public class TrackXmlHandler extends DefaultHandler {

    private static final String TRACK_ID = "trackId";
    private static final String TRACK_NAME = "trackName";
    private static final String TRACK_BAND_ID = "trackBandId";
    private static final String TRACK_TEMPO = "trackTempo";
    private static final String TRACK_DURATION = "trackDuration";
    private static final String TRACK_DETAILS = "trackDetails";
    private static final String TRACK_LINK = "trackLink";
    private static final String TRACK_RELEASE_DATE = "trackReleaseDate";
    private static final String TRACKS = "Tracks";
    private static final String ITEM = "item";
    @Getter
    private List<Track> trackList;
    private StringBuilder elementValue;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case TRACKS:
                trackList = new ArrayList<>();
                break;
            case ITEM:
                trackList.add(Track.builder()
                        .trackId(Integer.valueOf(attributes.getValue(TRACK_ID)))
                        .trackName("temp")
                        .build());
                break;
            case TRACK_NAME:
            case TRACK_BAND_ID:
            case TRACK_TEMPO:
            case TRACK_DURATION:
            case TRACK_DETAILS:
            case TRACK_LINK:
            case TRACK_RELEASE_DATE:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case TRACK_NAME:
                trackList.get(trackList.size() - 1).setTrackName(elementValue.toString());
                break;
            case TRACK_BAND_ID:
                trackList.get(trackList.size() - 1)
                        .setTrackBandId(isParsable(elementValue.toString()) ? Integer.parseInt(elementValue.toString()) : 0);
                break;
            case TRACK_TEMPO:
                trackList.get(trackList.size() - 1)
                        .setTrackTempo(isParsable(elementValue.toString()) ? Integer.parseInt(elementValue.toString()) : 0);
                break;
            case TRACK_DURATION:
                trackList.get(trackList.size() - 1)
                        .setTrackDuration(isParsable(elementValue.toString()) ? Integer.parseInt(elementValue.toString()) : 0);
                break;
            case TRACK_DETAILS:
                trackList.get(trackList.size() - 1).setTrackDetails(elementValue.toString());
                break;
            case TRACK_LINK:
                trackList.get(trackList.size() - 1).setTrackLink(elementValue.toString());
                break;
            case TRACK_RELEASE_DATE:
                try {
                    trackList.get(trackList.size() - 1).setTrackReleaseDate(LocalDate.parse(elementValue.toString()));
                }
                    catch(DateTimeParseException x){
                        trackList.get(trackList.size() - 1).setTrackReleaseDate(null);
                    }
             //   elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }
}
