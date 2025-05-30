package com.nomad.model;

import java.time.LocalDate;

public class Trip {
    //    -Trip ID
//    -Location ID
//    -Cost
//    -Description
//    -Dates
    private int tripId;
    private int userId;
    private int tripCost;
    private String description;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public Trip(int tripId, int userId, int tripCost, String description, LocalDate dateFrom, LocalDate dateTo) {
        this.tripId = tripId;
        this.userId = userId;
        this.tripCost = tripCost;
        this.description = description;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Trip() {}

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTripCost() {
        return tripCost;
    }

    public void setTripCost(int tripCost) {
        this.tripCost = tripCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}
