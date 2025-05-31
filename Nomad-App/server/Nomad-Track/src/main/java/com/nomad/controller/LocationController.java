package com.nomad.controller;


import com.nomad.dao.LocationDao;
import com.nomad.exception.DaoException;
import com.nomad.model.Location;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class LocationController {
    private LocationDao locationDao;
//    private LocationService locationService;

    //TODO: Add locationService
    public LocationController(LocationDao locationDao) {
        this.locationDao = locationDao;
//        this.locationService = locationService;
    }
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/locations/{locationId}", method = RequestMethod.GET)
    public Location getLocationById(@PathVariable int locationId) {
        try {
            Location location = locationDao.getLocationById(locationId);
            if (location == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
            }
            return location;
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(path = "/locations", method = RequestMethod.GET)
    public List<Location> getAllLocations() {
        try {
            return locationDao.getLocations();
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/locations", method = RequestMethod.POST)
    public Location makeNewlocation(@Valid @RequestBody Location incomingLocation) {
        try {
            if(incomingLocation == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location can't be null");
            }
            return locationDao.createLocation(incomingLocation);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/locations/{locationId}", method = RequestMethod.PUT)
    public Location updatelocation(@Valid @RequestBody Location changedLocation, @PathVariable int locationId) {
        changedLocation.setLocationId(locationId);

        try {
            if(changedLocation == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "location can't be null");
            }
            if(locationId <= 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id can't be zero or negative");
            }
            return locationDao.updateLocation(changedLocation);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }
}
