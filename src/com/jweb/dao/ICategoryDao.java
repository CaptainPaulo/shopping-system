package com.jweb.dao;

import com.jweb.beans.Category;

import java.util.List;

/**
 * Created by adenis_e on 17-4-6.
 */
public interface ICategoryDao {
    List<Category> getAll() throws DAOException;
    void create(Category category) throws DAOException;
    void update(Category category) throws DAOException;
    void delete(String id) throws DAOException;
    Category findById(String id) throws DAOException;
}
