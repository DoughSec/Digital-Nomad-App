package com.nomad.dao;

import com.nomad.model.Trip;
import com.nomad.model.User;

import java.util.List;

public interface TripDao {
    List<Trip> getTrips();
    Trip getTripById(int id);
    List<Trip> getTripsByUserId(int userId);
    Trip createTrip(Trip Trip, User user);
    Trip updateTrip(Trip changedTrip, User user);
    int deleteTripById(int id);
}
