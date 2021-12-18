INSERT INTO band(band_id, band_name, band_details) VALUES (1, 'MY COVER BAND', 'Alternative&metal'),
                                                          (2, 'MY BAND', 'metal'),
                                                          (3, 'MUSE', 'Rock');

INSERT INTO track (track_id, track_name, track_tempo, track_duration, track_details, track_link, track_release_date, track_band_id)
            VALUES (1, 'Track1', 120, 135, 'super track1', 'http://spotify.com/sdfc7436w&d', '2000-03-12', 1),
                   (2, 'Track2', 120, 200, 'super track2', 'http://spotify.com/sdfc74sds36&d', '2021-03-12', 1),
                   (3, 'Track3', 120, 135, 'super track3', 'http://spotify.com/sdfc74sdsd36&d', '2012-03-12', 2);

