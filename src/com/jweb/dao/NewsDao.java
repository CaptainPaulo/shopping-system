package com.jweb.dao;

import com.jweb.beans.News;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaetan on 06/01/16.
 */
public class NewsDao extends DAO implements INewsDao {
    private static final String SQL_INSERT = "INSERT INTO News (author, title, body, publicationDate) VALUES (?, ?, ?, ?)";
    private static final String SQL_GET_ALL = "SELECT * FROM News";

    NewsDao(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public List<News> getAll() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<News> news = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_GET_ALL, false);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                news.add(hydrate(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return news;
    }

    public void create(News news) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedID = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initPreparedRequest(connection, SQL_INSERT, true, news.getAuthor(),
                    news.getTitle(), news.getBody(), news.getPublicationDate());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Failing to create a news on the DB, nothing added.");
            }
            generatedID = preparedStatement.getGeneratedKeys();
            if (generatedID.next()) {
                news.setId(generatedID.getLong(1));
            } else {
                throw new DAOException("Failing to create a news on the DB, no ID generated.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeAll(generatedID, preparedStatement, connection);
        }
    }

    private static News hydrate(ResultSet resultSet) throws SQLException {
        News news = new News();
        news.setId(resultSet.getLong("id"));
        news.setAuthor(resultSet.getString("author"));
        news.setTitle(resultSet.getString("title"));
        news.setBody(resultSet.getString("body"));
        news.setPublicationDate(resultSet.getTimestamp("publicationDate"));
        return news;
    }
}
