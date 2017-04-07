package com.jweb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by gaetan on 29/12/15.
 */
public class DAOFactory {
    private static final String PROPERTIES_FILE = "/com/jweb/dao/dao.properties";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";

    private String url;
    private String username;
    private String password;

    private DAOFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DAOFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String username;
        String password;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            throw new DAOConfigurationException("The file " + PROPERTIES_FILE + " cannot be find.");
        }

        try {
            properties.load(propertiesFile);
            url = properties.getProperty(PROPERTY_URL);
            driver = properties.getProperty(PROPERTY_DRIVER);
            username = properties.getProperty(PROPERTY_USERNAME);
            password = properties.getProperty(PROPERTY_PASSWORD);
        } catch (IOException e) {
            throw new DAOConfigurationException("Cannot load the file " + PROPERTIES_FILE, e);
        }

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new DAOConfigurationException("Driver cannot be found in the classpath.", e);
        }

        return new DAOFactory(url, username, password);
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public  ICategoryDao getCategoryDao() {
        return new CategoryDao(this);
    }

    public IPannierDao getPannierDao() {
        return new PannierDao(this);
    }

    public IMemberDao getMemberDao() {
        return new MemberDao(this);
    }

    public INewsDao getNewsDao() {
        return new NewsDao(this);
    }

    public IProductDao getProductDao() {
        return new ProductDao(this);
    }

    public IReviewDao getReviewDao() {
        return new ReviewDao(this);
    }

}