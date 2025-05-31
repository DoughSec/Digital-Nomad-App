package com.nomad.controller;

import com.nomad.dao.LodgingDao;
import com.nomad.exception.DaoException;
import com.nomad.model.Lodging;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class LodgingController {

    private LodgingDao lodgingDao;
//    private LodgingService lodgingService;

    //TODO: Add lodgingService
    public LodgingController(LodgingDao lodgingDao) {
        this.lodgingDao = lodgingDao;
//        this.lodgingService = lodgingService;
    }
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/lodgings/{lodgingId}", method = RequestMethod.GET)
    public Lodging getLodgingById(@PathVariable int lodgingId) {
        try {
            Lodging lodging = lodgingDao.getLodgingById(lodgingId);
            if (lodging == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lodging not found");
            }
            return lodging;
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(path = "/lodgings", method = RequestMethod.GET)
    public List<Lodging> getAllLodgings() {
        try {
            return lodgingDao.getLodgings();
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/lodgings", method = RequestMethod.POST)
    public Lodging makeNewLodging(@Valid @RequestBody Lodging incomingLodging) {
        try {
            if(incomingLodging == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lodging can't be null");
            }
            return lodgingDao.createLodging(incomingLodging);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/lodgings/{lodgingId}", method = RequestMethod.PUT)
    public Lodging updateLodging(@Valid @RequestBody Lodging changedLodging, @PathVariable int lodgingId) {
        changedLodging.setLodgingId(lodgingId);

        try {
            if(changedLodging == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lodging can't be null");
            }
            if(lodgingId <= 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id can't be zero or negative");
            }
            return lodgingDao.updateLodging(changedLodging);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }
}
