package com.jweb.dao;

import com.jweb.beans.Review;

import java.util.List;

/**
 * Created by gaetan on 10/01/16.
 */
public interface IReviewDao {
    void create(Review review) throws DAOException;
    List<Review> getAll() throws DAOException;
}