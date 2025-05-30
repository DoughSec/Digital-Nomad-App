package com.nomad.model;

import java.time.LocalTime;

public class Flight {
    private int flightId;
    private int flightCost;
//    private int flightName;
    private int travelTime;
    private LocalTime startTime;
    private LocalTime endTime;

    public Flight(int flightId, int flightCost, int travelTime, LocalTime startTime, LocalTime endTime) {
        this.flightId = flightId;
        this.flightCost = flightCost;
        this.travelTime = travelTime;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Flight() {}

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getFlightCost() {
        return flightCost;
    }

    public void setFlightCost(int flightCost) {
        this.flightCost = flightCost;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
