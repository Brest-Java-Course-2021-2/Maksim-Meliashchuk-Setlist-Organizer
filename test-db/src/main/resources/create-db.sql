DROP TABLE IF EXISTS band;
CREATE TABLE band(
    band_id INT NOT NULL AUTO_INCREMENT,
    band_name VARCHAR(255) NOT NULL UNIQUE,
    band_details VARCHAR(255),
    PRIMARY KEY (band_id)
);

