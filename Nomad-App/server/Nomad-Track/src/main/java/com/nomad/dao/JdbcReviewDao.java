package com.nomad.dao;

import com.nomad.exception.DaoException;
import com.nomad.model.Review;
import com.nomad.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcReviewDao implements ReviewDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Review> getReviews() {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT review_id, rating, comment FROM reviews";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Review review = mapRowTOReview(results);
                reviews.add(review);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return reviews;
    }

    @Override
    public Review getReviewById(int id) {
        Review Review = null;
        String sql = "SELECT review_id, rating, comment FROM reviews WHERE review_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                Review = mapRowTOReview(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return Review;
    }

//    @Override
//    public List<Review> getReviewsByUserId(int userId) {
//        List<Review> Reviews = new ArrayList<>();
//        String sql = "SELECT Review_id, user_id, service_id, appointment_date, appointment_start_time, appointment_end_time FROM Reviews WHERE user_id = ?";
//        try {
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
//            while (results.next()) {
//                Review Review = mapRowTOReview(results);
//                Reviews.add(Review);
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        }
//        return Reviews;
//    }

    @Override
    public Review createReview(Review review) {
        Review newReview = null;
        String sql = "INSERT INTO reviews (rating, comment) VALUES (?, ?) RETURNING Review_id";
        try {
            //TODO
            //userClient.getUserId()
//            int ReviewId = jdbcTemplate.queryForObject(sql, int.class, 13, Review.getServiceId(), Review.getAppointmentDate(), Review.getAppointmentStartTime(), Review.getAppointmentEndTime());
//            newReview = getReviewById(ReviewId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newReview;
    }

    @Override
    public Review updateReview(Review changedReview) {
        Review updatedReview = null;
        String sql = "UPDATE Reviews SET review_id = ?, rating = ?, comment = ? WHERE review_id = ?";
        try {
            //TODO
//            int rowsAffected = jdbcTemplate.update(sql, changedReview.getReviewId(), changedReview.getUserId(), changedReview.getServiceId(), changedReview.getAppointmentDate(), changedReview.getAppointmentStartTime(), changedReview.getAppointmentEndTime(), changedReview.getReviewId(), userClient.getUserId());
//            if (rowsAffected == 0) {
//                throw new DaoException("Zero rows affected, expected at least one");
//            }
            updatedReview = getReviewById(changedReview.getReviewId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedReview;
    }

    @Override
    public int deleteReviewById(int id) {
        int numberOfRows = 0;
        String sql = "DELETE FROM reviews WHERE review_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Review mapRowTOReview(SqlRowSet rs) {
        Review review = new Review();
        review.setReviewId(rs.getInt("review_id"));
        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));
        return review;
    }
}
