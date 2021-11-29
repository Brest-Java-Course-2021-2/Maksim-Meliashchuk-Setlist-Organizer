package com.epam.brest.dao;

import org.springframework.jdbc.core.RowMapper;
import com.epam.brest.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TrackDaoJDBCImpl implements TrackDao{

    private final Logger logger = LogManager.getLogger(TrackDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String sqlAllTracks = "SELECT * FROM track";

    private String selectCountFromTrack = "SELECT COUNT(*) FROM track";

    private String sqlCreateTrack = "INSERT INTO track(track_name, track_details, track_tempo, track_duration, " +
            "track_link, track_release_date, track_band_id) " +
            "values(:trackName, :trackDetails, :trackTempo, :trackDuration, :trackLink, :trackReleaseDate, :trackBandId)";

    public TrackDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Track> findAll() {
        return namedParameterJdbcTemplate.query(sqlAllTracks, new TrackDaoJDBCImpl.TrackRowMapper());
    }

    @Override
    public Track getTrackById(Integer trackId) {
        return null;
    }

    @Override
    public Integer create(Track track) {
        logger.debug("Start: create({})", track);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("trackName", track.getTrackName())
                        .addValue("trackDetails", track.getTrackDetails())
                        .addValue("trackTempo", track.getTrackTempo())
                        .addValue("trackDuration", track.getTrackDuration())
                        .addValue("trackLink", track.getTrackLink())
                        .addValue("trackReleaseDate", track.getReleaseDate())
                        .addValue("trackBandId", track.getBandId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlCreateTrack, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    @Override
    public Integer update(Track track) {
        return null;
    }

    @Override
    public Integer delete(Integer trackId) {
        return null;
    }

    @Override
    public Integer count() {
        logger.debug("Track count()");
        return namedParameterJdbcTemplate
                .queryForObject(selectCountFromTrack, new MapSqlParameterSource(), Integer.class);
    }

    private class TrackRowMapper implements RowMapper<Track> {
        @Override
        public Track mapRow(ResultSet resultSet, int i) throws SQLException {
            logger.debug("Start: track mapRow");
            Track track = new Track();
            track.setTrackId(resultSet.getInt("track_id"));
            track.setTrackName(resultSet.getString("track_name"));
            track.setTrackTempo(resultSet.getInt("track_tempo"));
            track.setTrackDuration(resultSet.getInt("track_duration"));
            track.setTrackDetails(resultSet.getString("track_details"));
            track.setTrackLink(resultSet.getString("track_link"));
            track.setReleaseDate(LocalDate.parse(resultSet.getString("track_release_date")));
            track.setBandId(resultSet.getInt("track_band_id"));
            return track;
        }
    }

}
