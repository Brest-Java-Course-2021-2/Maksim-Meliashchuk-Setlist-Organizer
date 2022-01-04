# Setlist Organizer Application 

## Available REST endpoints

- [Version](#version)
- [Bands](#bands)
    * [Get all bands DTO](#get-all-bands-dto)
    * [Get all bands](#get-all-bands)
    * [Get a band](#get-a-band)
    * [Create a new band](#create-a-new-band)
    * [Update a band](#update-a-band)
    * [Delete a band](#delete-a-band)
- [Tracks](#tracks)
    * [Get all tracks DTO](#get-all-tracks-dto)
    * [Get several tracks DTO](#get-several-tracks-dto)
    * [Get all tracks](#get-all-tracks)
    * [Get a track](#get-a-track)
    * [Create a new track](#create-a-new-track)
    * [Update a track](#update-a-track)
    * [Delete a track](#delete-a-track)

### Version

Get information for the API version. 
Request sample cURL:

```bash
curl --request GET \
--url http://localhost:8088/version \
--header 'Content-Type: application/json'
```

<details>
  <summary>200 Response Example</summary>

A version

```bash
1.0.0
```

</details>

### Bands

#### Get all bands DTO

Get information for all bands with their repertoire duration and track count.
Request sample cURL:

```bash
curl --request GET \
--url http://localhost:8088/bands_dto \
--header 'Content-Type: application/json'
```

<details>
  <summary>200 Response Example</summary>

A set of bands

```bash
[
    {
        "bandId": 1,
        "bandName": "MY COVER BAND",
        "bandCountTrack": 1,
        "bandRepertoireDuration": 120000,
        "bandDetails": "Alternative&Metal"
    },
    {
        "bandId": 2,
        "bandName": "MY BAND",
        "bandCountTrack": 0,
        "bandRepertoireDuration": null,
        "bandDetails": "Metal"
    },
    {
        "bandId": 3,
        "bandName": "MUSE",
        "bandCountTrack": 3,
        "bandRepertoireDuration": 452000,
        "bandDetails": "Rock"
    }
]
```

</details>

#### Get all bands

Get information for all bands based on their IDs.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/bands \
--header 'Content-Type: application/json'
```

<details>
  <summary>200 Response Example</summary>

A set of bands

```bash
[
    {
        "bandId": 1,
        "bandName": "MY COVER BAND",
        "bandDetails": "Alternative&Metal"
    },
    {
        "bandId": 2,
        "bandName": "MY BAND",
        "bandDetails": "Metal"
    },
    {
        "bandId": 3,
        "bandName": "MUSE",
        "bandDetails": "Rock"
    }
]
```

</details>

#### Get a band

Get information for a single band identified by its unique ID.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/bands/1 \
--header 'Content-Type: application/json'
```

<details>
  <summary>200 Response Example</summary>

A band

```bash
{
    "bandId": 1,
    "bandName": "MY COVER BAND",
    "bandDetails": "Alternative&Metal"
}
```

</details>

<details>
  <summary>404 Response Example</summary>

Trying to get a non-existent band

```bash
{
    "message": "band.not_found",
    "details": [
        "Band not found for id: 12"
    ]
}
```

</details>

#### Create a new band

Request sample cURL:
```bash
curl --request POST \
--url http://localhost:8088/bands \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
  "bandName": "New band",
  "bandDetails": "New style"
}'
```

<details>
  <summary>200 Response Example</summary>

Band have been created. Returns the ID of the new band.

```bash
4
```

</details>

<details>
  <summary>400 Response Example</summary>

An attempt to create band with invalid fields

```bash
{
    "timestamp": "2022-01-04T17:08:56.492101",
    "status": 400,
    "errors": [
        "Band name size have to be <= 100 symbols!",
        "Band details size have to be <= 1000 symbols!"
    ]
}
```

</details>

<details>
  <summary>422 Response Example</summary>

An attempt to create a non-unique band

```bash
{
    "message": "data_base_error",
    "details": [
        "NotUniqueException: Band 'MUSE' already exists in Data Base!"
    ]
}
```

</details>

#### Update a band

Request sample cURL:
```bash
curl --request PUT \
--url http://localhost:8088/bands \
--header 'Content-Type: application/json' \
--data-raw '{
  "bandId": 1,
  "bandName": "Old band",
  "bandDetails": "Old style"
}'
```

<details>
  <summary>200 Response Example</summary>

Band(s) have been updated. Returns the number of bands affected.

```bash
1
```

</details>

<details>
  <summary>404 Response Example</summary>

Trying to update a non-existent band

```bash
{
    "message": "band.not_found",
    "details": [
        "Band not found for id: 12"
    ]
}
```

</details>

#### Delete a band

Request sample cURL:
```bash
curl --request DELETE \
--url http://localhost:8088/bands/2 \
--header 'Content-Type: application/json'
```

<details>
  <summary>200 Response Example</summary>

Band(s) have been removed. Returns the number of bands affected.

```bash
1
```

</details>

<details>
  <summary>404 Response Example</summary>

Trying to delete a non-existent band

```bash
{
    "message": "band.not_found",
    "details": [
        "Band not found for id: 12"
    ]
}
```

</details>

<details>
  <summary>422 Response Example</summary>

Attempting to delete a band with linked tracks

```bash
{
    "message": "data_base_error",
    "details": [
        "JdbcSQLIntegrityConstraintViolationException: Referential integrity constraint violation: \"TRACK_BAND_FK: PUBLIC.TRACK FOREIGN KEY(TRACK_BAND_ID) REFERENCES PUBLIC.BAND(BAND_ID) (1)\"; SQL statement:\nDELETE FROM band WHERE band_id = ? [23503-200]"
    ]
}
```

</details>

### Tracks

#### Get all tracks DTO

Get information for all tracks with their band names.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/tracks_dto \
--header 'Content-Type: application/json'
```

<details>
  <summary>200 Response Example</summary>

A set of tracks

```bash
[
    {
        "trackId": 1,
        "trackName": "Drones",
        "trackBandName": "MUSE",
        "trackTempo": 104,
        "trackDuration": 135000,
        "trackDetails": "Tuning:EADGBe",
        "trackLink": "https://www.youtube.com/watch?v=rvX7lgrx47M&ab_channel=Muse-Topic",
        "trackReleaseDate": "2000-01-12"
    },
    {
        "trackId": 2,
        "trackName": "Uprising",
        "trackBandName": "MUSE",
        "trackTempo": 129,
        "trackDuration": 200000,
        "trackDetails": "[SYNTH BASS]",
        "trackLink": "https://www.youtube.com/watch?v=w8KQmps-Sog&ab_channel=Muse",
        "trackReleaseDate": "2021-03-12"
    },
    {
        "trackId": 3,
        "trackName": "Absolution",
        "trackBandName": "MUSE",
        "trackTempo": 90,
        "trackDuration": 117000,
        "trackDetails": "[Preset 51]",
        "trackLink": "https://www.youtube.com/watch?v=Mp6W0IzLlW8&ab_channel=TheMuse",
        "trackReleaseDate": "2012-02-12"
    },
    {
        "trackId": 4,
        "trackName": "Time Is Running Out",
        "trackBandName": "MY COVER BAND",
        "trackTempo": 104,
        "trackDuration": 120000,
        "trackDetails": "with chords as replacement or the distorted guitar",
        "trackLink": "https://www.youtube.com/watch?v=O2IuJPh6h_A&ab_channel=Muse",
        "trackReleaseDate": "2012-07-12"
    }
]
```

</details>

#### Get several tracks DTO

Get information for tracks with their release dates between {fromDate} and {toDate}.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/repertoire/filter?fromDate=2007-12-14&toDate=2020-12-30 \
--header 'Content-Type: application/json'
```

<details>
  <summary>200 Response Example</summary>

A set of tracks

```bash
[
    {
        "trackId": 3,
        "trackName": "Absolution",
        "trackBandName": "MUSE",
        "trackTempo": 90,
        "trackDuration": 117000,
        "trackDetails": "[Preset 51]",
        "trackLink": "https://www.youtube.com/watch?v=Mp6W0IzLlW8&ab_channel=TheMuse",
        "trackReleaseDate": "2012-02-12"
    },
    {
        "trackId": 4,
        "trackName": "Time Is Running Out",
        "trackBandName": "MY COVER BAND",
        "trackTempo": 104,
        "trackDuration": 120000,
        "trackDetails": "with chords as replacement or the distorted guitar",
        "trackLink": "https://www.youtube.com/watch?v=O2IuJPh6h_A&ab_channel=Muse",
        "trackReleaseDate": "2012-07-12"
    }
]
```

</details>

#### Get all tracks

Get information for all tracks based on their IDs.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/repertoire \
--header 'Content-Type: application/json'
```

<details>
  <summary>200 Response Example</summary>

A set of tracks

```bash
[
    {
        "trackId": 1,
        "trackName": "Drones",
        "trackBandId": 3,
        "trackTempo": 104,
        "trackDuration": 135000,
        "trackDetails": "Tuning:EADGBe",
        "trackLink": "https://www.youtube.com/watch?v=rvX7lgrx47M&ab_channel=Muse-Topic",
        "trackReleaseDate": "2000-01-12"
    },
    {
        "trackId": 2,
        "trackName": "Uprising",
        "trackBandId": 3,
        "trackTempo": 129,
        "trackDuration": 200000,
        "trackDetails": "[SYNTH BASS]",
        "trackLink": "https://www.youtube.com/watch?v=w8KQmps-Sog&ab_channel=Muse",
        "trackReleaseDate": "2021-03-12"
    },
    {
        "trackId": 3,
        "trackName": "Absolution",
        "trackBandId": 3,
        "trackTempo": 90,
        "trackDuration": 117000,
        "trackDetails": "[Preset 51]",
        "trackLink": "https://www.youtube.com/watch?v=Mp6W0IzLlW8&ab_channel=TheMuse",
        "trackReleaseDate": "2012-02-12"
    },
    {
        "trackId": 4,
        "trackName": "Time Is Running Out",
        "trackBandId": 1,
        "trackTempo": 104,
        "trackDuration": 120000,
        "trackDetails": "with chords as replacement or the distorted guitar",
        "trackLink": "https://www.youtube.com/watch?v=O2IuJPh6h_A&ab_channel=Muse",
        "trackReleaseDate": "2012-07-12"
    }
]
```

</details>

#### Get a track

Get information for a single track identified by its unique ID.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/repertoire/1 \
--header 'Content-Type: application/json'
```


<details>
  <summary>200 Response Example</summary>

A track

```bash
{
    "trackId": 1,
    "trackName": "Drones",
    "trackBandId": 3,
    "trackTempo": 104,
    "trackDuration": 135000,
    "trackDetails": "Tuning:EADGBe",
    "trackLink": "https://www.youtube.com/watch?v=rvX7lgrx47M&ab_channel=Muse-Topic",
    "trackReleaseDate": "2000-01-12"
}
```

</details>

<details>
  <summary>404 Response Example</summary>

Trying to get a non-existent track

```bash
{
    "message": "track.not_found",
    "details": [
        "Track not found for id: 12"
    ]
}
```

</details>

#### Create a new track

Request sample cURL:
```bash
curl --request POST \
--url http://localhost:8088/repertoire \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
  "trackName": "New Track",
  "trackDetails": "new trackDetails",
  "trackBandId": "1",
  "trackDuration": "303000",
  "trackTempo": "86",
  "trackLink": "https://www.youtube.com/watch?v=Q0oIoR9mLwc&ab_channel=RedHotChiliPeppers",
  "trackReleaseDate": "2016-06-17"
}'
```

<details>
  <summary>200 Response Example</summary>

Track have been created. Returns the ID of the new track.

```bash
5
```

</details>

<details>
  <summary>400 Response Example</summary>

An attempt to create track with invalid fields.

```bash
{
    "timestamp": "2022-01-04T17:25:33.252092",
    "status": 400,
    "errors": [
        "Track tempo cannot be less than zero!",
        "Track duration cannot be less than zero!",
        "Track details size have to be <= 2000 symbols!",
        "Track link is not valid. The link must contain http or https!",
        "Band id should be positive",
        "Please provide track name!"
    ]
}
```

</details>

#### Update a track

Request sample cURL:
```bash
curl --request PUT \
--url http://localhost:8088/repertoire \
--header 'Content-Type: application/json' \
--data-raw '{
  "trackId": 1,
  "trackName": "Edited track",
  "trackDetails": "Edited trackDetails",
  "trackBandId": "2",
  "trackDuration": "304000",
  "trackTempo": "90",
  "trackLink": "https://www.youtube.com/watch?v=Q0oIoR9mLwc&ab_channel=RedHotChiliPeppers",
  "trackReleaseDate": "2016-07-17"
}'
```

<details>
  <summary>200 Response Example</summary>

Track(s) have been updated. Returns the number of tracks affected.

```bash
1
```

</details>

<details>
  <summary>400 Response Example</summary>

Trying to update track with invalid fields

```bash
{
    "timestamp": "2022-01-04T17:25:33.252092",
    "status": 400,
    "errors": [
        "Track tempo cannot be less than zero!",
        "Track duration cannot be less than zero!",
        "Track details size have to be <= 2000 symbols!",
        "Track link is not valid. The link must contain http or https!",
        "Band id should be positive",
        "Please provide track name!"
    ]
}
```

</details>

<details>
  <summary>404 Response Example</summary>

Trying to update a non-existent track

```bash
{
    "message": "track.not_found",
    "details": [
        "Track not found for id: 12"
    ]
}
```

</details>


#### Delete a track

Request sample cURL:
```bash
curl --request DELETE \
--url http://localhost:8088/repertoire/2 \
--header 'Content-Type: application/json'
```

<details>
  <summary>200 Response Example</summary>

Track(s) have been removed. Returns the number of tracks affected.

```bash
1
```

</details>

<details>
  <summary>404 Response Example</summary>

Trying to delete a non-existent track

```bash
{
    "message": "track.not_found",
    "details": [
        "Track not found for id: 12"
    ]
}
```

</details>