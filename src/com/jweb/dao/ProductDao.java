package com.jweb.dao;

import com.jweb.beans.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by gaetan on 10/01/16.
 */
public class ProductDao extends DAO implements IProductDao {
    private static final String SQL_FIND_BY_ID = "SELECT * FROM product WHERE id = ?";

    ProductDao(DAOFactory daoFactory) {
        super(daoFactory);
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
        return product;
    }
}
