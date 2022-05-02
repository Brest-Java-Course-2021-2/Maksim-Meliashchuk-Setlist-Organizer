package com.epam.brest.service.sax;

import com.epam.brest.model.Band;
import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class BandXmlHandler extends DefaultHandler {

    private static final String BAND_ID = "bandId";
    private static final String BAND_NAME = "bandName";
    private static final String BAND_DETAILS = "bandDetails";
    private static final String BANDS = "Bands";
    private static final String ITEM = "item";
    @Getter
    private List<Band> bandList;

    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case BANDS:
                bandList = new ArrayList<>();
                break;
            case ITEM:
                bandList.add(Band.builder()
                        .bandId(Integer.valueOf(attributes.getValue(BAND_ID)))
                        .bandName("temp")
                        .build());
                break;
            case BAND_NAME:
            case BAND_DETAILS:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case BAND_NAME:
                bandList.get(bandList.size() - 1).setBandName(elementValue.toString());
                break;
            case BAND_DETAILS:
                bandList.get(bandList.size() - 1).setBandDetails(elementValue.toString());
                break;
        }
    }
}
