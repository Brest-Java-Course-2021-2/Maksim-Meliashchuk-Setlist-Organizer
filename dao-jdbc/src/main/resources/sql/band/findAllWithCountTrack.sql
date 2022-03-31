SELECT band_id AS bandId,band_name AS bandName, band_details AS bandDetails,
    COUNT(track.track_band_id) AS bandCountTrack,
    SUM(track.track_duration) AS bandRepertoireDuration
FROM band LEFT JOIN track ON band.band_id=track.track_band_id GROUP BY band_id, band_name, band_details
