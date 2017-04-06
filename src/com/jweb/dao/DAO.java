package com.jweb.dao;

import java.sql.*;

/**
 * Created by gaetan on 06/01/16.
 */
abstract class DAO {
    DAOFactory daoFactory;

    DAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    static PreparedStatement initPreparedRequest(Connection connection, String sql, boolean returnGeneratedKeys, Object... objects) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i + 1, objects[i]);
        }
        return preparedStatement;
    }

    private static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println("Failing to close ResultSet: " + e.getMessage());
            }
        }
    }

    void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("Failing to close Statement: " + e.getMessage());
            }
        }
    }

    void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failing to cloe the connection: " + e.getMessage());
            }
        }
    }

    void closeAll(ResultSet resultSet, Statement statement, Connection connection) {
        closeResultSet(resultSet);
        closeStatement(statement);
        closeConnection(connection);
    }
}
