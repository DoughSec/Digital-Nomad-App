package com.nomad.model;

import java.time.LocalDate;

public class Trip {
    //    -Trip ID
//    -Location ID
//    -Cost
//    -Description
//    -Dates
    private int tripId;
    private int cost;
    private String description;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public Trip(int tripId, int cost, String description, LocalDate dateFrom, LocalDate dateTo) {
        this.tripId = tripId;
        this.cost = cost;
        this.description = description;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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
