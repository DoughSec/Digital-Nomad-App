package com.nomad.services;

import com.nomad.model.Trip;

import java.util.List;

public interface TripService {
    boolean checkValidTripId(List<Trip> trips, int tripId);
    Trip getTrip(int id);
    Trip add(Trip newTrip);
    boolean update(Trip updatedTrip);
    boolean delete(int tripId);

}
