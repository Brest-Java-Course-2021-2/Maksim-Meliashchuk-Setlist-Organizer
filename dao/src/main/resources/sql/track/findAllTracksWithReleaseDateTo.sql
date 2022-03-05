SELECT track_id, track_name, track_details, track_band_id, track_tempo, track_duration, track_link, track_release_date,
    band.band_name as trackBandName from track left join band on track.track_band_id = band.band_id
    WHERE track_release_date <= :toDate

