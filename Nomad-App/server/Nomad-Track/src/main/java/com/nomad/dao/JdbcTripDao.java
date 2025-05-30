package com.nomad.dao;

import com.nomad.exception.DaoException;
import com.nomad.model.Trip;
import com.nomad.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTripDao implements TripDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcTripDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Trip> getTrips() {
        List<Trip> Trips = new ArrayList<>();
        String sql = "SELECT trip_id, user_id, trip_cost, description, date_from, date_to FROM trips";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Trip Trip = mapRowTOTrip(results);
                Trips.add(Trip);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return Trips;
    }

    @Override
    public Trip getTripById(int id) {
        Trip Trip = null;
        String sql = "SELECT trip_id, user_id, trip_cost, description, date_from, date_to FROM trips WHERE trip_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                Trip = mapRowTOTrip(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return Trip;
    }

    @Override
    public List<Trip> getTripsByUserId(int userId) {
        List<Trip> Trips = new ArrayList<>();
        String sql = "SELECT trip_id, user_id, trip_cost, description, date_from, date_to FROM trips WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Trip Trip = mapRowTOTrip(results);
                Trips.add(Trip);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return Trips;
    }

    @Override
    public Trip createTrip(Trip Trip, User user) {
        Trip newTrip = null;
        String sql = "INSERT INTO trips (user_id, trip_cost, description, date_from, date_to) VALUES (?, ?, ?, ?, ?) RETURNING trip_id";
        try {
            //TODO
            //userClient.getUserId()
//            int TripId = jdbcTemplate.queryForObject(sql, int.class, 13, Trip.getServiceId(), Trip.getAppointmentDate(), Trip.getAppointmentStartTime(), Trip.getAppointmentEndTime());
//            newTrip = getTripById(TripId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newTrip;
    }

    @Override
    public Trip updateTrip(Trip changedTrip, User user) {
        Trip updatedTrip = null;
        String sql = "UPDATE trips SET trip_id = ?, user_id = ?, trip_cost = ?, description = ?, date_from = ?, date_to = ? WHERE trip_id = ? AND user_id = ?";
        try {
            //TODO
//            int rowsAffected = jdbcTemplate.update(sql, changedTrip.getTripId(), changedTrip.getUserId(), changedTrip.getServiceId(), changedTrip.getAppointmentDate(), changedTrip.getAppointmentStartTime(), changedTrip.getAppointmentEndTime(), changedTrip.getTripId(), userClient.getUserId());
//            if (rowsAffected == 0) {
//                throw new DaoException("Zero rows affected, expected at least one");
//            }
            updatedTrip = getTripById(changedTrip.getTripId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedTrip;
    }

    @Override
    public int deleteTripById(int id) {
        int numberOfRows = 0;
        String sql = "DELETE FROM trips WHERE trip_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Trip mapRowTOTrip(SqlRowSet rs) {
        Trip trip = new Trip();
        trip.setTripId(rs.getInt("trip_id"));
        trip.setUserId(rs.getInt("user_id"));
        trip.setTripCost(rs.getInt("trip_cost"));
        trip.setDescription(rs.getString("description"));
        trip.setDateFrom(rs.getDate("date_from").toLocalDate());
        trip.setDateTo(rs.getDate("date_to").toLocalDate());
        return trip;
    }
}
