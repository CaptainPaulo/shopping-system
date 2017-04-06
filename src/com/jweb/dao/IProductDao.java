package com.jweb.dao;

import com.jweb.beans.Product;

import java.util.List;

/**
 * Created by gaetan on 10/01/16.
 */
public interface IProductDao {
    Product findById(String id) throws DAOException;
    void update(Product product) throws DAOException;
    void delete(String id) throws DAOException;
    void create(Product product) throws DAOException;
    List<Product> getAll() throws DAOException;
}
