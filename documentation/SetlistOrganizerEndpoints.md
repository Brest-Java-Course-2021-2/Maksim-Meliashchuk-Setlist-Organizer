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
### Bands

#### Get all bands DTO

Get information for all bands with their repertoire duration and track count.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/bands_dto \
--header 'Content-Type: application/json'
```

#### Get all bands

Get information for all bands based on their IDs.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/bands \
--header 'Content-Type: application/json'
```

#### Get a band

Get information for a single band identified by its unique ID.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/bands/1 \
--header 'Content-Type: application/json'
```

#### Create a new band

Request sample cURL:
```bash
curl --request POST \
--url http://localhost:8088/bands \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
"bandName": "New band"
}'
```

#### Update a band

Request sample cURL:
```bash
curl --request PUT \
--url http://localhost:8088/bands \
--header 'Content-Type: application/json' \
--data-raw '{
   "bandId": 1,
   "bandName": "Old band"
}'
```

#### Delete a band

Request sample cURL:
```bash
curl --request DELETE \
--url http://localhost:8088/bands/1 \
--header 'Content-Type: application/json'
```

### Tracks

#### Get all tracks DTO

Get information for all tracks with their band names.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/tracks_dto \
--header 'Content-Type: application/json'
```

#### Get several tracks DTO

Get information for tracks with their release dates between {fromDate} and {toDate}.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/repertoire/filter?fromDate=2007-12-14&toDate=2020-12-30 \
--header 'Content-Type: application/json'
```


#### Get all tracks

Get information for all tracks based on their IDs.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/repertoire \
--header 'Content-Type: application/json'
```

#### Get a track

Get information for a single track identified by its unique ID.
Request sample cURL:
```bash
curl --request GET \
--url http://localhost:8088/repertoire/1 \
--header 'Content-Type: application/json'
```

#### Create a new track

Request sample cURL:
```bash
curl --request POST \
--url http://localhost:8088/repertoire \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
"trackName": "New Track"
}'
```
#### Update a track

Request sample cURL:
```bash
curl --request PUT \
--url http://localhost:8088/repertoire \
--header 'Content-Type: application/json' \
--data-raw '{
   "trackId": 1,
   "trackName": "Edited track"
}'
```

#### Delete a track

Request sample cURL:
```bash
curl --request DELETE \
--url http://localhost:8088/repertoire/2 \
--header 'Content-Type: application/json'
```