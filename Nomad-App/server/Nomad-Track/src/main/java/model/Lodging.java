package com.techelevator.model;

public class Lodging {
    private int lodgingId;
    private int lodgingCostPerNight;
    private int totalLodgingCost;
    private int distanceFromAirport;
    private int nightsStayed;

    public Lodging(int lodgingId, int lodgingCostPerNight, int totalLodgingCost, int distanceFromAirport, int nightsStayed) {
        this.lodgingId = lodgingId;
        this.lodgingCostPerNight = lodgingCostPerNight;
        this.totalLodgingCost = totalLodgingCost;
        this.distanceFromAirport = distanceFromAirport;
        this.nightsStayed = nightsStayed;
    }

    public int getLodgingId() {
        return lodgingId;
    }

    public void setLodgingId(int lodgingId) {
        this.lodgingId = lodgingId;
    }

    public int getLodgingCostPerNight() {
        return lodgingCostPerNight;
    }

    public void setLodgingCostPerNight(int lodgingCostPerNight) {
        this.lodgingCostPerNight = lodgingCostPerNight;
    }

    public int getTotalLodgingCost() {
        return totalLodgingCost;
    }

    public void setTotalLodgingCost() {
        this.totalLodgingCost = this.lodgingCostPerNight * this.nightsStayed;
    }

    public int getDistanceFromAirport() {
        return distanceFromAirport;
    }

    public void setDistanceFromAirport(int distanceFromAirport) {
        this.distanceFromAirport = distanceFromAirport;
    }

    public int getNightsStayed() {
        return nightsStayed;
    }

    public void setNightsStayed(int nightsStayed) {
        this.nightsStayed = nightsStayed;
    }
}
