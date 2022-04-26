DROP TABLE IF EXISTS band cascade;
DROP TABLE IF EXISTS track cascade;
CREATE TABLE IF NOT EXISTS band(
    band_id INT NOT NULL AUTO_INCREMENT,
    band_name VARCHAR(100) NOT NULL UNIQUE,
    band_details VARCHAR(1000),
    PRIMARY KEY (band_id)
);

CREATE TABLE IF NOT EXISTS track (
    track_id INT NOT NULL AUTO_INCREMENT,
    track_name VARCHAR(100) NOT NULL,
    track_tempo INT,
    track_duration INT,
    track_details VARCHAR(2000),
    track_link VARCHAR(255),
    track_release_date DATE,
    track_band_id INT,
    CONSTRAINT track_pk PRIMARY KEY (track_id),
    CONSTRAINT track_band_fk FOREIGN KEY (track_band_id) REFERENCES band(band_id)
);

