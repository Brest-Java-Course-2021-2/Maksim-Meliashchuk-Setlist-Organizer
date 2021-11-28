package com.epam.brest.dao;

import org.springframework.jdbc.core.RowMapper;
import com.epam.brest.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TrackDaoJDBCImpl implements TrackDao{

    private final Logger logger = LogManager.getLogger(TrackDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String sqlAllTracks = "SELECT * FROM track";


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
        return null;
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
        return null;
    }

    private class TrackRowMapper implements RowMapper<Track> {
        @Override
        public Track mapRow(ResultSet resultSet, int i) throws SQLException {
            logger.debug("Start: mapRow");
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
