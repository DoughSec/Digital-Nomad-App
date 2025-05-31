package com.nomad.dao;

import com.nomad.model.Flight;
import com.nomad.model.User;

import java.util.List;

public interface FlightDao {
    List<Flight> getFlights();
    Flight getFlightById(int id);
    Flight createFlight(Flight flight);
    Flight updateFlight(Flight changedFlight);
    int deleteFlightById(int id);
}
