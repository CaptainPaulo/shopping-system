package com.jweb.dao;

/**
 * Created by gaetan on 29/12/15.
 */
public class DAOConfigurationException extends RuntimeException {
    public DAOConfigurationException(String message) {
        super(message);
    }

    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }
}
