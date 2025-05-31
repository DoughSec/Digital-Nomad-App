package com.nomad.controller;

import com.nomad.dao.FlightDao;
import com.nomad.exception.DaoException;
import com.nomad.model.Flight;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class FlightController {
    private FlightDao flightDao;
    private Flight flight;

    public FlightController(FlightDao flightDao, Flight flight) {
        this.flightDao = flightDao;
        this.flight = flight;
    }
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/flights/{flightId}", method = RequestMethod.GET)
    public Flight getFlightById(@PathVariable int flightId) {
        try {
            Flight flight = flightDao.getFlightById(flightId);
            if (flight == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");
            }
            return flight;
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(path = "/flights", method = RequestMethod.GET)
    public List<Flight> getAllFlights() {
        try {
            return flightDao.getFlights();
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/flights", method = RequestMethod.POST)
    public Flight makeNewFlight(@Valid @RequestBody Flight incomingFlight) {
        try {
            if(incomingFlight == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flight can't be null");
            }
            return flightDao.createFlight(incomingFlight);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/flights/{flightId}", method = RequestMethod.PUT)
    public Flight updateFlight(@Valid @RequestBody Flight changedFlight, @PathVariable int flightId) {
        changedFlight.setFlightId(flightId);

        try {
            if(changedFlight == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flight can't be null");
            }
            if(flightId <= 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id can't be zero or negative");
            }
            return flightDao.updateFlight(changedFlight);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }
}
