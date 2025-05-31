package com.nomad.dao;

import com.nomad.exception.DaoException;
import com.nomad.model.Location;
import com.nomad.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcLocationDao implements LocationDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcLocationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Location> getLocations() {
        List<Location> Locations = new ArrayList<>();
        String sql = "SELECT location_id, review_id, city, address, imgurl FROM locations";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Location Location = mapRowTOLocation(results);
                Locations.add(Location);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return Locations;
    }

    @Override
    public Location getLocationById(int id) {
        Location Location = null;
        String sql = "SELECT location_id, review_id, city, address, imgurl FROM locations WHERE location_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                Location = mapRowTOLocation(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return Location;
    }

    @Override
    public Location createLocation(Location location) {
        Location newLocation = null;
        String sql = "INSERT INTO locations (review_id, city, address, imgurl) VALUES (?, ?, ?, ?) RETURNING location_id";
        try {
            int locationId = jdbcTemplate.queryForObject(sql, int.class, location.getReviewId(), location.getCity(), location.getAddress(), location.getImgURL());
            newLocation = getLocationById(locationId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newLocation;
    }

    @Override
    public Location updateLocation(Location changedLocation) {
        Location updatedLocation = null;
        String sql = "UPDATE locations SET location_id = ?, review_id = ?, city = ?, address = ?, imgurl = ? WHERE location_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, changedLocation.getLocationId(), changedLocation.getReviewId(), changedLocation.getCity(), changedLocation.getAddress(), changedLocation.getImgURL(), changedLocation.getLocationId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedLocation = getLocationById(changedLocation.getLocationId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedLocation;
    }

    @Override
    public int deleteLocationById(int id) {
        int numberOfRows = 0;
        String sql = "DELETE FROM locations WHERE location_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Location mapRowTOLocation(SqlRowSet rs) {
        Location location = new Location();
        location.setLocationId(rs.getInt("location_id"));
        location.setReviewId(rs.getInt("review_id"));
        location.setCity(rs.getString("city"));
        location.setAddress(rs.getString("address"));
        location.setImgURL(rs.getString("imgurl"));
        return location;
    }
}
