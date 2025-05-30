package com.nomad.model;

public class Review {
    //    -Review ID
//    -Rating (out of 5)
//    -Comment
    private int reviewId;
    private double rating;
    private String comment;

    public Review(int reviewId, double rating, String comment) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
    }

    public Review() {}

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
