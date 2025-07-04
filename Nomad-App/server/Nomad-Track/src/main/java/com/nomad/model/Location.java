package com.nomad.model;

import java.util.List;

public class Location {
    //    -Location ID
//    -city
//    -Address
//    -Review(overall)
//    -Photo?
    private int locationId;
    private String city;
    private String address;

    //TODO: personal review or overall reviews???
    private int reviewId;

    //TODO: How will I access images here???
    private String imgURL;

    public Location(int locationId, String city, String address, int reviewId, String imgURL) {
        this.locationId = locationId;
        this.city = city;
        this.address = address;
        this.reviewId = reviewId;
        this.imgURL = imgURL;
    }

    public Location() {}

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
