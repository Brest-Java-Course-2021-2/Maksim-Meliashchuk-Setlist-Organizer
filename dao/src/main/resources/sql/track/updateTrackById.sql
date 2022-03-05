UPDATE track SET track_name=:trackName, track_details=:trackDetails, track_tempo=:trackTempo, track_duration=:trackDuration,
    track_link=:trackLink, track_release_date=:trackReleaseDate, track_band_id=:trackBandId
    WHERE track_id = :trackId