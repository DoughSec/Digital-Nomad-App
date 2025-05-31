package com.nomad.controller;

import com.nomad.dao.ReviewDao;
import com.nomad.exception.DaoException;
import com.nomad.model.Review;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class ReviewController {

    private ReviewDao reviewDao;
    private Review review;

    public ReviewController(ReviewDao reviewDao, Review review) {
        this.reviewDao = reviewDao;
        this.review = review;
    }
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/reviews/{reviewId}", method = RequestMethod.GET)
    public Review getReviewById(@PathVariable int reviewId) {
        try {
            Review review = reviewDao.getReviewById(reviewId);
            if (review == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
            }
            return review;
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(path = "/reviews", method = RequestMethod.GET)
    public List<Review> getAllReviews() {
        try {
            return reviewDao.getReviews();
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/reviews", method = RequestMethod.POST)
    public Review makeNewReview(@Valid @RequestBody Review incomingReview) {
        try {
            if(incomingReview == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review can't be null");
            }
            return reviewDao.createReview(incomingReview);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/reviews/{reviewId}", method = RequestMethod.PUT)
    public Review updateReview(@Valid @RequestBody Review changedReview, @PathVariable int reviewId) {
        changedReview.setReviewId(reviewId);
        try {
            if(changedReview == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review can't be null");
            }
            if(reviewId <= 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id can't be zero or negative");
            }
            return reviewDao.updateReview(changedReview);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }
}
