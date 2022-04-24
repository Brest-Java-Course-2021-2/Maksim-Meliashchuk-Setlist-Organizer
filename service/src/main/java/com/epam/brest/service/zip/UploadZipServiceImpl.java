package com.epam.brest.service.zip;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadZipServiceImpl implements UploadZipService {
    private static final String BANDS_FILE = "Bands.xml";
    private static final String TRACKS_FILE = "Tracks.xml";
    private static final List<String> FILES = List.of(BANDS_FILE, TRACKS_FILE);
    private final Map<String, String> listOfFiles = new HashMap<>();

    @Override
    public Map<String, String> uploadZipFile(MultipartFile files) throws IOException {
        log.debug("uploadZipFile({})", files.getName());
        try (var zipInputStream = new ZipInputStream(files.getInputStream())) {
            ZipEntry nextEntry;
            while ((nextEntry = zipInputStream.getNextEntry()) != null) {
                var fileName = nextEntry.getName();
                if (!FILES.contains(fileName)) {
                    throw new RuntimeException(format("Import of unsupported file name [%s] is not allowed", fileName));
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream, StandardCharsets.UTF_8));
                listOfFiles.put(fileName, reader.lines().collect(Collectors.joining()));
                zipInputStream.closeEntry();
            }
            return listOfFiles;
        }
    }
}
