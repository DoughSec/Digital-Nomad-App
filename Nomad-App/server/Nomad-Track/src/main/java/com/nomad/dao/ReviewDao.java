package com.nomad.dao;

import com.nomad.model.Review;
import com.nomad.model.User;

import java.util.List;

public interface ReviewDao {
    List<Review> getReviews();
    Review getReviewById(int id);
    Review createReview(Review Review);
    Review updateReview(Review changedReview);
    int deleteReviewById(int id);
}
