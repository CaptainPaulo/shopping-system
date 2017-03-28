package com.jweb.dao;

import com.jweb.beans.Product;

/**
 * Created by gaetan on 10/01/16.
 */
public interface IProductDao {
    Product findById(String id) throws DAOException;
}
