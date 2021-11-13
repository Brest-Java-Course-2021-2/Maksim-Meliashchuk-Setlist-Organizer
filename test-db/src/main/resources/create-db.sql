DROP TABLE IF EXISTS band;
DROP TABLE IF EXISTS track;
CREATE TABLE band(
    band_id INT NOT NULL AUTO_INCREMENT,
    band_name VARCHAR(100) NOT NULL UNIQUE,
    band_details VARCHAR(1000),
    PRIMARY KEY (band_id)
);

CREATE TABLE track (
    track_id int NOT NULL AUTO_INCREMENT,
    track_name varchar(100) NOT NULL,
    track_tempo INT,
    track_duration INT,
    track_details varchar(2000) NOT NULL,
    track_link varchar(255) NOT NULL,
    track_release_date DATE,
    track_band_id INT,
    CONSTRAINT track_pk PRIMARY KEY (track_id),
    CONSTRAINT track_band_fk FOREIGN KEY (track_band_id) REFERENCES band(band_id)
);

