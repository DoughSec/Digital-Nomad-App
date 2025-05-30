package com.nomad.dao;

import com.nomad.exception.DaoException;
import com.nomad.model.Flight;
import com.nomad.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcFlightDao implements FlightDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcFlightDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Flight> getFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT flight_id, flight_cost, travel_time, flight_start_time, flight_end_time FROM flights";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Flight flight = mapRowTOFlight(results);
                flights.add(flight);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return flights;
    }

    @Override
    public Flight getFlightById(int id) {
        Flight flight = null;
        String sql = "SELECT flight_id, flight_cost, travel_time, flight_start_time, flight_end_time FROM flights WHERE flight_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                flight = mapRowTOFlight(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return flight;
    }


    @Override
    public Flight createFlight(Flight flight, User user) {
        Flight newFlight = null;
        String sql = "INSERT INTO flights (flight_cost, travel_time, flight_start_time, flight_end_time) VALUES (?, ?, ?, ?) RETURNING flight_id";
        try {
            //TODO
            //userClient.getUserId()
//            int FlightId = jdbcTemplate.queryForObject(sql, int.class, 13, Flight.getServiceId(), Flight.getAppointmentDate(), Flight.getAppointmentStartTime(), Flight.getAppointmentEndTime());
//            newFlight = getFlightById(FlightId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newFlight;
    }

    @Override
    public Flight updateFlight(Flight changedFlight) {
        Flight updatedFlight = null;
        String sql = "UPDATE flights SET flight_id = ?, flight_cost = ?, travel_time = ?, flight_start_time = ?, flight_end_time = ? WHERE flight_id = ?";
        try {
            //TODO
//            int rowsAffected = jdbcTemplate.update(sql, changedFlight.getFlightId(), changedFlight.getUserId(), changedFlight.getServiceId(), changedFlight.getAppointmentDate(), changedFlight.getAppointmentStartTime(), changedFlight.getAppointmentEndTime(), changedFlight.getFlightId(), userClient.getUserId());
//            if (rowsAffected == 0) {
//                throw new DaoException("Zero rows affected, expected at least one");
//            }
            updatedFlight = getFlightById(changedFlight.getFlightId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedFlight;
    }

    @Override
    public int deleteFlightById(int id) {
        int numberOfRows = 0;
        String sql = "DELETE FROM flights WHERE flight_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Flight mapRowTOFlight(SqlRowSet rs) {
        Flight flight = new Flight();
        flight.setFlightId(rs.getInt("flight_id"));
        flight.setFlightCost(rs.getInt("flight_cost"));
        flight.setTravelTime(rs.getInt("travel_time"));
        flight.setStartTime(rs.getTime("flight_start_time").toLocalTime());
        flight.setEndTime(rs.getTime("flight_end_time").toLocalTime());
        return flight;
    }
    
}
