package com.jweb.dao;

import com.jweb.beans.Pannier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adenis_e on 17-4-6.
 */
public class PannierDao extends DAO implements IPannierDao {
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Pannier WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO Pannier (member_id, product_id, number_of_product, buy) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM Pannier WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE Member SET member_id = ?, product_id = ?, number_of_product = ?, buy = ? WHERE id = ?";
    private static final String SQL_GET_ALL = "SELECT * FROM Pannier";
    private static final String SQL_GET_BY_BUY = "SELECT * FROM Pannier WHERE buy = ? AND id = ?";

    PannierDao(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public List<Pannier> getAll() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Pannier> panniers = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_GET_ALL, false);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                panniers.add(hydrate(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return panniers;
    }

    public List<Pannier> getByBuy(boolean buy, String id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Pannier> panniers = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_GET_BY_BUY, false,
                    buy,
                    id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                panniers.add(hydrate(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return panniers;
    }

    public void create(Pannier pannier) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedID = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_INSERT, true,
                    pannier.getMember(),
                    pannier.getProduct(),
                    pannier.getNumberOfProduct(),
                    pannier.isBuy());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Failing to create a pannier on the DB, nothing added.");
            }
            generatedID = preparedStatement.getGeneratedKeys();
            if (generatedID.next()) {
                pannier.setId(generatedID.getLong(1));
            } else {
                throw new DAOException("Failing to create a pannier on the DB, no ID generated.");
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
                throw new DAOException("Error while deleting the pannier, pannier not deleted.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public void update(Pannier pannier) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_UPDATE, false,
                    pannier.getMember(),
                    pannier.getProduct(),
                    pannier.getNumberOfProduct(),
                    pannier.isBuy());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Error while updating the pannier, pannier not updated.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public Pannier findById(String id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Pannier pannier = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_FIND_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                pannier = hydrate(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return pannier;
    }

    private Pannier hydrate(ResultSet resultSet) throws SQLException {
        Pannier pannier = new Pannier();
        pannier.setId(resultSet.getLong("id"));
        pannier.setMember(resultSet.getLong("member_id"));
        pannier.setProduct(resultSet.getLong("product_id"));
        pannier.setNumberOfProduct(resultSet.getLong("number_of_product"));
        pannier.setBuy(resultSet.getBoolean("buy"));
        return pannier;
    }
}
