package com.jweb.dao;

import com.jweb.beans.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adenis_e on 17-4-6.
 */
public class CategoryDao extends DAO implements ICategoryDao {
    private static final String SQL_FIND_BY_ID = "SELECT * FROM Category WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Category WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE Category SET name = ? WHERE id = ?";
    private final static String SQL_INSERT = "INSERT INTO Category (name) VALUES (?)";
    private final static String SQL_GET_ALL = "SELECT * FROM Category";

    CategoryDao(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public List<Category> getAll() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Category> categories = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_GET_ALL, false);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categories.add(hydrate(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return categories;
    }

    public void create(Category category) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedID = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_INSERT, true,
                    category.getName());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Failing to create a category on the DB, nothing added.");
            }
            generatedID = preparedStatement.getGeneratedKeys();
            if (generatedID.next()) {
                category.setId(generatedID.getLong(1));
            } else {
                throw new DAOException("Failing to create a category on the DB, no ID generated.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(generatedID, preparedStatement, connection);
        }
    }

    public void update(Category category) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_UPDATE, false,
                    category.getName());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Error while updating the category, category not updated.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public void delete(String id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_DELETE_BY_ID, false, id);
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Error while deleting the category, category not deleted.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public Category findById(String id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Category category = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_FIND_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                category = hydrate(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return category;
    }

    private Category hydrate(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setName(resultSet.getString("name"));
        return category;
    }
}
