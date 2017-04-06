package com.jweb.dao;

import com.jweb.beans.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaetan on 29/12/15.
 */
public class MemberDao extends DAO implements IMemberDao {
    private static final String SQL_INSERT = "INSERT INTO Member (email, password, firstName, lastName, newsletter) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM Member WHERE email = ?";
    private static final String SQL_GET_ALL = "SELECT * FROM Member";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM Member WHERE id = ?";
    private static final String SQL_EMAIL_AND_PASSWORD_MATCH = "SELECT COUNT(*) as nbr FROM Member WHERE email = ? AND password = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Member WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE Member SET email = ?, firstName = ?, lastName = ?, newsletter = ? WHERE id = ?";

    MemberDao(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public void update(Member member) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_UPDATE, false,
                    member.getEmail(),
                    member.getFirstName(),
                    member.getLastName(),
                    member.isNewsletter(),
                    member.getId());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Error while updating the member, member not updated.");
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
                throw new DAOException("Error while deleting the member, member not deleted.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public boolean emailAndPasswordMatch(String email, String password) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_EMAIL_AND_PASSWORD_MATCH, false, email, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return (resultSet.getInt("nbr") != 0);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return false;
    }

    public List<Member> getAll() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Member> members = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_GET_ALL, false);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                members.add(hydrate(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return members;
    }


    public void create(Member member) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedID = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_INSERT, true,
                    member.getEmail(),
                    member.getPassword(),
                    member.getFirstName(),
                    member.getLastName(),
                    member.isNewsletter());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Failing to create a user on the DB, nothing added.");
            }
            generatedID = preparedStatement.getGeneratedKeys();
            if (generatedID.next()) {
                member.setId(generatedID.getLong(1));
            } else {
                throw new DAOException("Failing to create a user on the DB, no ID generated.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(generatedID, preparedStatement, connection);
        }
    }

    public Member findById(String id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Member member = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_FIND_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                member = hydrate(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return member;
    }

    public Member findByEmail(String email) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Member member = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_FIND_BY_EMAIL, false, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                member = hydrate(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return member;
    }

    private static Member hydrate(ResultSet resultSet) throws SQLException {
        Member member = new Member();
        member.setId(resultSet.getLong("id"));
        member.setEmail(resultSet.getString("email"));
        member.setPassword(resultSet.getString("password"));
        member.setFirstName(resultSet.getString("firstName"));
        member.setLastName(resultSet.getString("lastName"));
        member.setNewsletter(resultSet.getBoolean("newsletter"));
        member.setAdmin(resultSet.getBoolean("isAdmin"));
        return member;
    }
}