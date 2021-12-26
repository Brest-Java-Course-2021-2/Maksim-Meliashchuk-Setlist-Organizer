INSERT INTO band(band_id, band_name, band_details) VALUES (1, 'MY COVER BAND', 'Alternative&metal'),
                                                          (2, 'MY BAND', 'metal'),
                                                          (3, 'MUSE', 'Rock');

INSERT INTO track (track_id, track_name, track_tempo, track_duration, track_details, track_link, track_release_date, track_band_id)
            VALUES (1, 'Drones', 104, 135, 'Tuning:EADGBe', 'https://www.youtube.com/watch?v=rvX7lgrx47M&ab_channel=Muse-Topic', '2000-03-12', 3),
                   (2, 'Uprising', 129, 200, '[SYNTH BASS]', 'https://www.youtube.com/watch?v=w8KQmps-Sog&ab_channel=Muse', '2021-03-12', 3),
                   (3, 'Absolution', 90, 117, '[Preset 51]', 'https://www.youtube.com/watch?v=Mp6W0IzLlW8&ab_channel=TheMuse', '2012-03-12', 3),
                   (4, 'Time Is Running Out', 104, 120, 'with chords as replacement or the distorted guitar', 'https://www.youtube.com/watch?v=O2IuJPh6h_A&ab_channel=Muse', '2012-03-12', 1);

