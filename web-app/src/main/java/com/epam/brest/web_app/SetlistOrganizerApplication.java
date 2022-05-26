package com.epam.brest.web_app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class SetlistOrganizerApplication {

    public static void main(String[] args) throws IOException {
        URL url = null;
        for (int i = 1; i < 20; i++) {
            try {
                TimeUnit.SECONDS.sleep(5);
                url = new URL("http://localhost:8484/auth/realms/setlist_organizer_realm");
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                int responseCode = huc.getResponseCode();
                SpringApplication.run(SetlistOrganizerApplication.class, args);
                break;
            } catch (Exception e) {
                log.debug(url + " connection refused");
            }
        }
        log.debug("end of time");
    }

}
