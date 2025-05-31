package com.nomad.controller;

import com.nomad.dao.TripDao;
import com.nomad.dao.UserDao;
import com.nomad.exception.DaoException;
import com.nomad.model.Trip;
import com.nomad.model.User;
import com.nomad.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class TripController {

    private TripDao tripDao;
//    private TripService tripService;
    private UserService userService;
    private UserDao userDao;

    public TripController(TripDao tripDao, UserDao userDao, UserService userService) {
        this.tripDao = tripDao;
        this.userDao = userDao;
//        this.TripService = tripService;
        this.userService = userService;
    }


    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "/trips/{tripId}", method = RequestMethod.GET)
    public Trip getTripById(@PathVariable int TripId) {
        try {
            Trip trip = tripDao.getTripById(TripId);
            if (trip == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found");
            }
            return trip;
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @RequestMapping(path = "/trips/users", method = RequestMethod.GET)
    public List<Trip> getTripsByUserId(Principal principal) {
        //TODO fix
        User user = userDao.getUserByUsername(principal.getName());
        try {
            List<Trip> trips = tripDao.getTripsByUserId(user.getId());
            if (trips == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found");
            }
            return trips;
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "/trips", method = RequestMethod.GET)
    public List<Trip> getAllTrips() {
        try {
            return tripDao.getTrips();
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    //TODO: Fix all Crud access points

//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
//    @ResponseStatus(HttpStatus.OK)
//    @RequestMapping(path = "/Trips/{TripId}", method = RequestMethod.PUT)
//    public Trip updateTrip(@Valid @RequestBody Trip changedTrip, @PathVariable int tripId, Principal principal) {
//        String name = principal.getName();
//        try {
//            if(!TripService.checkValidTripId(getAllTrips(), tripId)) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must be a valid Trip id");
//            }
//            //makes sure client picks a valid service
//            if(!TripService.isValidService(serviceDao.getServices(), changedTrip.getServiceId())) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must select a valid service id");
//            }
//            //checks if the appointment is within availability
//            if(changedTrip.getServiceId() <= 0) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must select a valid service id");
//            }
//            if(!TripService.isAppointmentTimeValid(changedTrip)) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment/Trip must be within Reagan's availability");
//            }
//            //overlapping Trip time check
//            if(TripService.isAppointmentOverlapping(changedTrip, getAllTrips())) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time/Date overlap");
//            }
//            //checks if someone is Trip in the past, must be a future Trip
//            if(!TripService.isAppointmentDateValid(changedTrip)) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment can't be made before current date, or the day of the Trip");
//            }
//            Trip TripToUpdate = getTripById(TripId);
//            if(TripToUpdate == null) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip doesn't exist");
//            }
//            UserClient userClient = userDao.getUserClient(name);
//            if(userClient.getUserId() != getTripById(TripId).getUserId()) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only change your own Trip/invalid request");
//            }
//            changedTrip.setUserId(userClient.getUserId());
//            changedTrip.setTripId(TripId);
//            return TripDao.updateTrip(changedTrip, userClient);
//        }
//        catch (DaoException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
//        }
//    }

//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @RequestMapping(path = "/trips/{tripId}", method = RequestMethod.DELETE)
//    public void deleteTrip(@PathVariable int tripId, Principal principal) {
//        try {
//            if(!TripService.checkValidTripId(getAllTrips(), tripId)) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must be a valid Trip id");
//            }
////            UserClient userClient = userDao.getUserClient(principal.getName());
//            Trip TripToDelete = TripDao.getTripById(TripId);
//            if(TripToDelete == null) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip doesn't exist or invalid request");
//            }
////            if(TripToDelete.getUserId() != userClient.getUserId()) {
////                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only delete your own Trip");
////            }
//            int TripDeleted = TripDao.deleteTripById(TripId);
//            if (TripDeleted == 0) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//            }
//        }
//        catch (DaoException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
//        }
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path = "/Trips/users", method = RequestMethod.POST)
//    public Trip createUserTrip(@Valid @RequestBody Trip incomingTrip, Principal principal) {
//        String name = principal.getName();
//        try {
//            //makes sure client picks a valid service
//            if(!TripService.isValidService(serviceDao.getServices(), incomingTrip.getServiceId())) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must select a valid service id");
//            }
//            UserClient userClient = userDao.getUserClient(name);
//            //checks if the appointment is within availability
//            if(!TripService.isAppointmentTimeValid(incomingTrip)) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment/Trip must be within Reagan's availability");
//            }
////            if(userClient == null) {
////                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must add yourself as a client first for phone number verification");
////            }
//            //overlapping Trip time check
//            if(TripService.isAppointmentOverlapping(incomingTrip, getAllTrips())) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time/Date overlap");
//            }
//            if(!TripService.isAppointmentDateValid(incomingTrip)) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment can't be made before current date, or the day of the Trip");
//            }
//            return TripDao.createTrip(incomingTrip, userClient);
//        }
//        catch (DaoException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
//        }
//    }
}
