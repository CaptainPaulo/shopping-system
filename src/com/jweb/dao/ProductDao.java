package com.jweb.dao;

import com.jweb.beans.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaetan on 10/01/16.
 */
public class ProductDao extends DAO implements IProductDao {
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Product WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO Product (name, description, price, category_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM Product WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE Member SET name = ?, description = ?, price = ?, category_id = ? WHERE id = ?";
    private static final String SQL_GET_ALL = "SELECT * FROM Product";

    ProductDao(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public List<Product> getAll() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> product = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_GET_ALL, false);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product.add(hydrate(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return product;
    }

    public void create(Product product) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedID = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_INSERT, true,
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCategory());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Failing to create a product on the DB, nothing added.");
            }
            generatedID = preparedStatement.getGeneratedKeys();
            if (generatedID.next()) {
                product.setId(generatedID.getLong(1));
            } else {
                throw new DAOException("Failing to create a product on the DB, no ID generated.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(generatedID, preparedStatement, connection);
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
                throw new DAOException("Error while deleting the product, product not deleted.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public void update(Product product) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_UPDATE, false,
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCategory());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Error while updating the product, product not updated.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public Product findById(String id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_FIND_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = hydrate(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return product;
    }

    private static Product hydrate(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getFloat("price"));
        product.setCategory(resultSet.getLong("category_id"));
        return product;
    }
}
