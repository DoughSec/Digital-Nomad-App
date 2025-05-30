package com.nomad.dao;

import com.nomad.exception.DaoException;
import com.nomad.model.Lodging;
import com.nomad.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcLodgingDao implements LodgingDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcLodgingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Lodging> getLodgings() {
        List<Lodging> Lodgings = new ArrayList<>();
        String sql = "SELECT lodging_id, lodging_cost_per_night, total_lodging_cost, distance_from_airport, nights_to_stay FROM lodgings";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Lodging Lodging = mapRowTOLodging(results);
                Lodgings.add(Lodging);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return Lodgings;
    }

    @Override
    public Lodging getLodgingById(int id) {
        Lodging Lodging = null;
        String sql = "SELECT lodging_id, lodging_cost_per_night, total_lodging_cost, distance_from_airport, nights_to_stay FROM lodgings WHERE lodging_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                Lodging = mapRowTOLodging(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return Lodging;
    }

//    @Override
//    public List<Lodging> getLodgingsByUserId(int userId) {
//        List<Lodging> Lodgings = new ArrayList<>();
//        String sql = "SELECT Lodging_id, user_id, service_id, appointment_date, appointment_start_time, appointment_end_time FROM Lodgings WHERE user_id = ?";
//        try {
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
//            while (results.next()) {
//                Lodging Lodging = mapRowTOLodging(results);
//                Lodgings.add(Lodging);
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        }
//        return Lodgings;
//    }

    @Override
    public Lodging createLodging(Lodging lodging) {
        Lodging newLodging = null;
        String sql = "INSERT INTO lodgings (lodging_cost_per_night, total_lodging_cost, distance_from_airport, nights_to_stay) VALUES (?, ?, ?, ?) RETURNING lodging_id";
        try {
            //TODO
            //userClient.getUserId()
//            int LodgingId = jdbcTemplate.queryForObject(sql, int.class, 13, Lodging.getServiceId(), Lodging.getAppointmentDate(), Lodging.getAppointmentStartTime(), Lodging.getAppointmentEndTime());
//            newLodging = getLodgingById(LodgingId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newLodging;
    }

    @Override
    public Lodging updateLodging(Lodging changedLodging) {
        Lodging updatedLodging = null;
        String sql = "UPDATE lodgings SET lodging_id = ?, lodging_cost_per_night = ?, total_lodging_cost = ?, distance_from_airport = ?, nights_to_stay = ? WHERE lodging_id = ?";
        try {
            //TODO
//            int rowsAffected = jdbcTemplate.update(sql, changedLodging.getLodgingId(), changedLodging.getUserId(), changedLodging.getServiceId(), changedLodging.getAppointmentDate(), changedLodging.getAppointmentStartTime(), changedLodging.getAppointmentEndTime(), changedLodging.getLodgingId(), userClient.getUserId());
//            if (rowsAffected == 0) {
//                throw new DaoException("Zero rows affected, expected at least one");
//            }
            updatedLodging = getLodgingById(changedLodging.getLodgingId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedLodging;
    }

    @Override
    public int deleteLodgingById(int id) {
        int numberOfRows = 0;
        String sql = "DELETE FROM lodgings WHERE lodging_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Lodging mapRowTOLodging(SqlRowSet rs) {
        Lodging lodging = new Lodging();
        lodging.setLodgingId(rs.getInt("lodging_id"));
        lodging.setLodgingCostPerNight(rs.getInt("lodging_cost_per_night"));
//     TODO   lodging.setTotalLodgingCost(rs.getInt("total_lodging_cost"));
        lodging.setDistanceFromAirport(rs.getInt("distance_from_airport"));
        lodging.setNightsToStay(rs.getInt("nights_to_stay"));
        return lodging;
    }
}
