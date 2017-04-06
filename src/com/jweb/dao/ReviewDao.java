package com.jweb.dao;

import com.jweb.beans.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaetan on 10/01/16.
 */
public class ReviewDao extends DAO implements IReviewDao {
    private static final String SQL_INSERT = "INSERT INTO Review (author, note, description, product_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_GET_ALL = "SELECT * FROM Review";

    ReviewDao(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public List<Review> getAll() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Review> reviews = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_GET_ALL, false);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reviews.add(hydrate(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return reviews;
    }

    public void create(Review review) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedID = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_INSERT, true, review.getAuthor(),
                    review.getNote(), review.getDescription(), review.getProductId());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Failing to create a review on the DB, nothing added.");
            }
            generatedID = preparedStatement.getGeneratedKeys();
            if (generatedID.next()) {
                review.setId(generatedID.getLong(1));
            } else {
                throw new DAOException("Failing to create a review on the DB, no ID generated.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(generatedID, preparedStatement, connection);
        }
    }

    private static Review hydrate(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getLong("id"));
        review.setAuthor(resultSet.getString("author"));
        review.setNote(resultSet.getInt("note"));
        review.setDescription(resultSet.getString("description"));
        review.setProductId(resultSet.getLong("product_id"));
        return review;
    }
}
